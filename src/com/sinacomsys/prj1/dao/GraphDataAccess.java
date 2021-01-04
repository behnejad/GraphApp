package com.sinacomsys.prj1.dao;

import com.sinacomsys.prj1.model.Graph;

public interface GraphDataAccess {
    boolean graphExists(Graph graph);

    boolean storeGraph(Graph graph);
}
