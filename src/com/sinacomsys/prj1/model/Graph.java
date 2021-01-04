package com.sinacomsys.prj1.model;

import java.text.Normalizer;
import java.util.*;

public class Graph {
    private List<Integer> nodes;
    private List<Vector> vectors;

    public Graph() {
        nodes = new ArrayList<>();
        vectors = new ArrayList<>();
    }

    public Graph(List<Integer> nodes, List<Vector> vectors) {
        this.nodes = nodes;
        this.vectors = vectors;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    private boolean validateGraph() {
        return false;
    }

    public void addNode(int node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
        }
    }

    public void addEdge(int s, int d) {
        boolean exist = false;
        for (Vector v : vectors) {
            if (v.getSource() == s && v.getDestination() == d) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            vectors.add(new Vector(s, d));
        }
    }

    public String getUniqDescriptor() {
        Formatter formatter = new Formatter(Locale.US);

        formatter.format("%d=", nodes.size());
        Collections.sort(nodes);
        for (int nodeId : nodes) {
            formatter.format("%d-", nodeId);
        }

        formatter.format("%d=", vectors.size());

        vectors.sort((o1, o2) -> {
            if (o1.getSource() > o2.getSource()) {
                return 1;
            } else if (o1.getSource() < o2.getSource()) {
                return -1;
            } else if (o1.getDestination() > o2.getDestination()) {
                return 1;
            } else {
                return 0;
            }
        });

        for (Vector vector : vectors) {
            formatter.format("%d:%d-", vector.getSource(), vector.getDestination());
        }

        return formatter.toString();
    }

    public int shortestPath(int s, int d) {
        return 0;
    }
}
