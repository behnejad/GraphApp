package com.sinacomsys.prj1;

import com.sinacomsys.prj1.dao.GraphDataAccess;
import com.sinacomsys.prj1.dao.SQLiteDataAccess;
import com.sinacomsys.prj1.model.Graph;
import com.sinacomsys.prj1.parser.GraphParser;
import com.sinacomsys.prj1.parser.ParserFactory;
import com.sinacomsys.prj1.parser.ParserType;

import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private JPanel panel1;
    private JButton searchBtn;
    private JComboBox storageBackend;
    private JTextArea inputGraph;
    private JComboBox inputFormat;
    private JButton storeButton;
    private JTextField idInput;
    private JButton retrieveButton;
    private JButton calculateButton;
    private JTextField sourceId;
    private JTextField destId;

    private static final String DB_PATH = "graph.db";

    public GUI() {
        setTitle("GraphApp");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inputFormat.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (inputFormat.getItemAt(inputFormat.getSelectedIndex()).equals("XML")) {
                    JOptionPane.showMessageDialog(this, "XML parser is not implemented.");
                    inputFormat.setSelectedIndex(0);
                }
            }
        });
        storageBackend.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (storageBackend.getItemAt(storageBackend.getSelectedIndex()).equals("FILE")) {
                    JOptionPane.showMessageDialog(this, "FILE backend is not implemented.");
                    storageBackend.setSelectedIndex(0);
                }
            }
        });
        searchBtn.addActionListener(e -> {
            Graph graph = parseGraph();
            if (graph == null) {
                JOptionPane.showMessageDialog(this, "Wrong format.");
            } else if (searchGraph(graph)) {
                JOptionPane.showMessageDialog(this, "Graph exists.");
            } else {
                JOptionPane.showMessageDialog(this, "Graph does not exist.");
            }
        });
        storeButton.addActionListener(e -> {
            Graph graph = parseGraph();
            if (graph == null) {
                JOptionPane.showMessageDialog(this, "Wrong format.");
            } else if (searchGraph(graph)) {
                JOptionPane.showMessageDialog(this, "Graph exists.");
            } else {
                storeGraph(graph);
            }
        });
        retrieveButton.addActionListener(e -> {
            inputGraph.setText("");
            String id = idInput.getText();
            if (id != null) {
                int i = Integer.parseInt(id);
                inputGraph.setText(getGraph(i));
            }
        });
        calculateButton.addActionListener(e -> {
            int s, d;
            String source = sourceId.getText();

            if (source != null && source.length() > 0) {
                s = Integer.parseInt(source);
            }
            else {
                return;
            }

            String dest = sourceId.getText();
            if (dest != null && dest.length() > 0) {
                d = Integer.parseInt(dest);
            } else {
                return;
            }

            Graph graph = parseGraph();
            if (graph != null) {
                JOptionPane.showMessageDialog(this, "shortest path length is " + graph.shortestPath(s, d));
            }
        });
    }

    private void storeGraph(Graph graph) {
        if (storageBackend.getSelectedIndex() == 0) {
            GraphDataAccess graphDataAccess = new SQLiteDataAccess(DB_PATH);
            int id = graphDataAccess.storeGraph(graph);
            if (id > 0) {
                JOptionPane.showMessageDialog(this, "Graph saved with idInput " + id);
            } else {
                JOptionPane.showMessageDialog(this, "Error on saving graph.");
            }
        }
    }

    private Graph parseGraph() {
        Graph graph = null;

        if (inputFormat.getSelectedIndex() == 0) {
            GraphParser graphParser = ParserFactory.getParser(ParserType.JSON);
            graph = graphParser.parse(inputGraph.getText());
        }

        return graph;
    }

    private boolean searchGraph(Graph graph) {
        if (storageBackend.getSelectedIndex() == 0) {
            GraphDataAccess graphDataAccess = new SQLiteDataAccess(DB_PATH);
            return graphDataAccess.graphExists(graph);
        }

        return false;
    }

    private String getGraph(int id) {
        if (storageBackend.getSelectedIndex() == 0) {
            GraphDataAccess graphDataAccess = new SQLiteDataAccess(DB_PATH);
            return graphDataAccess.getGraph(id);
        }

        return null;
    }
}
