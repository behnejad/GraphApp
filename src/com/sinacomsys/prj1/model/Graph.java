package com.sinacomsys.prj1.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int id;
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

    private boolean validateGraph() {
        return false;
    }
}
