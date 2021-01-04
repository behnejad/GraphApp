package com.sinacomsys.prj1.parser;

import com.sinacomsys.prj1.model.Graph;

public interface GraphParser {
    Graph parse(String input);
    String composer(Graph graph);
}
