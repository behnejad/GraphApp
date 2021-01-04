package com.sinacomsys.prj1.dao;

import com.sinacomsys.prj1.model.Graph;
import com.sinacomsys.prj1.parser.ParserFactory;
import com.sinacomsys.prj1.parser.ParserType;

import java.sql.*;

public class SQLiteDataAccess implements GraphDataAccess {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Can not load sqlite driver");
        }
    }

    private static final String CREATE_HASH_TABlE = "create table if not exists graph (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, hash text, json text);";
    private static final String SEARCH_GRAPH = "select count(1) from graph where hash = ? limit 1;";
    private static final String STORE_GRAPH = "insert into graph (hash, json) values (?, ?);";
    private static final String GET_GRAPH = "select json from graph where id = ? limit 1;";

    private String dbPath;

    public SQLiteDataAccess(String dbPath) {
        this.dbPath = "jdbc:sqlite:" + dbPath;
        prepareDataBase();
    }

    private void prepareDataBase() {
        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_HASH_TABlE);
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean graphExists(Graph graph) {
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement stmt = conn.prepareStatement(SEARCH_GRAPH, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, graph.getUniqDescriptor());
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public int storeGraph(Graph graph) {
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement stmt = conn.prepareStatement(STORE_GRAPH, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, graph.getUniqDescriptor());
            stmt.setString(2, ParserFactory.getParser(ParserType.JSON).composer(graph));
            if (stmt.executeUpdate() > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public String getGraph(int id) {
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement stmt = conn.prepareStatement(GET_GRAPH, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
