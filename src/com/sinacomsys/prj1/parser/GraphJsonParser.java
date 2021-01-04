package com.sinacomsys.prj1.parser;

import com.sinacomsys.prj1.model.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;

public class GraphJsonParser implements GraphParser {
    public GraphJsonParser() {
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
