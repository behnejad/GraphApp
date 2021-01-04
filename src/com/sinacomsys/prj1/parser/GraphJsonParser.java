package com.sinacomsys.prj1.parser;

import com.sinacomsys.prj1.model.Graph;
import com.sinacomsys.prj1.model.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GraphJsonParser implements GraphParser {
    public GraphJsonParser() {
    }

    @Override
    public String composer(Graph graph) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("node", new JSONArray(graph.getNodes()));

        ArrayList<JSONObject> edges = new ArrayList<>();
        for (Vector v : graph.getVectors()) {
            edges.add(new JSONObject().put("source", v.getSource()).put("destination", v.getDestination()));
        }

        jsonObject.put("edges", edges);
        return jsonObject.toString();
    }

    @Override
    public Graph parse(String input) {
        try {
            Graph g;

            JSONObject jsonObject = new JSONObject(input);
            if (jsonObject == null) {
                return null;
            }

            if (!jsonObject.has("node")) {
                return null;
            } else {
                g = new Graph();
                Object nodes = jsonObject.get("node");

                if (!(nodes instanceof JSONArray)) {
                    return null;
                } else {
                    for (Object s : (JSONArray) nodes) {
                        if (s instanceof Integer) {
                            g.addNode((Integer) s);
                        } else {
                            return null;
                        }
                    }
                }
            }

            if (!jsonObject.has("edges")) {
                return null;
            } else {
                Object vector = jsonObject.get("edges");

                if (!(vector instanceof JSONArray)) {
                    return null;
                }
                else {
                    for (Object s : (JSONArray) vector) {
                        if (s instanceof JSONObject) {
                            if (((JSONObject) s).has("source") && ((JSONObject) s).has("destination") &&
                                    ((JSONObject) s).get("source") instanceof Integer &&
                                    ((JSONObject) s).get("destination") instanceof Integer) {
                                g.addEdge(((JSONObject) s).getInt("source"), ((JSONObject) s).getInt("destination"));
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }
            }

            return g;
        } catch (Exception e) {
            return null;
        }
    }
}
