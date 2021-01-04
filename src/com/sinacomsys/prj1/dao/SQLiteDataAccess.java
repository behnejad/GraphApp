package com.sinacomsys.prj1.dao;

import com.sinacomsys.prj1.model.Graph;

import java.sql.*;

public class SQLiteDataAccess implements GraphDataAccess {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Can not load sqlite driver");
        }
    }

    private static final String CREATE_HASH_TABlE = "create table if not exists graphHash (hash text);";
    private static final String CREATE_GRAPH = "";

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
        return false;
    }

    @Override
    public boolean storeGraph(Graph graph) {
        return false;
    }
}
