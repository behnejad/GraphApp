package com.sinacomsys.prj1.parser;

import com.sinacomsys.prj1.model.Graph;
import org.json.JSONObject;

public class GraphJsonParser implements GraphParser {
    public GraphJsonParser() {
    }

    @Override
    public Graph parse(String input) {
        JSONObject jsonObject = new JSONObject(input);
        return null;
    }
}
