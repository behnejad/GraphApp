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
            vectors.add(new Vector(Integer.min(s, d), Integer.max(s, d)));
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
        HashMap<Integer, java.util.Vector<Integer>> nodes_vector = new HashMap<>();
        for (Vector v : vectors) {
            if (nodes_vector.containsKey(v.getSource())) {
                java.util.Vector<Integer> neighbors = nodes_vector.get(v.getSource());
                if (!neighbors.contains(v.getDestination())) {
                    neighbors.add(v.getDestination());
                }
            } else {
                java.util.Vector<Integer> value = new java.util.Vector<>();
                value.add(v.getDestination());
                nodes_vector.put(v.getSource(), value);
            }

            if (nodes_vector.containsKey(v.getDestination())) {
                java.util.Vector<Integer> neighbors = nodes_vector.get(v.getDestination());
                if (!neighbors.contains(v.getSource())) {
                    neighbors.add(v.getSource());
                }
            } else {
                java.util.Vector<Integer> value = new java.util.Vector<>();
                value.add(v.getSource());
                nodes_vector.put(v.getDestination(), value);
            }
        }
        HashSet<Integer> visited = new HashSet<>();
        visited.add(s);
        return dfs(s, d, 0, visited, nodes_vector);
    }

    private int dfs(int s, int d, int depth, Set<Integer> visited, HashMap<Integer, java.util.Vector<Integer>> map) {
        if (s == d) {
            return depth;
        }

        if (!map.containsKey(s)) {
            return -1;
        }

        int min = -1;

        int local;
        for (Integer neighbor : map.get(s)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                local = dfs(neighbor, d, depth + 1, visited, map);
                visited.remove(neighbor);
                if (min == -1) {
                    min = local;
                } else {
                    min = Integer.min(min, local);
                }
            }
        }

        return min;
    }
}
