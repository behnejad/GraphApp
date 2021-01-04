package com.sinacomsys.prj1.parser;

public class ParserFactory {
    private static final GraphParser jsonParser = new GraphJsonParser();

    public static GraphParser getParser(ParserType type) {
        if (type == ParserType.JSON) {
            return jsonParser;
        } else {
            return null;
        }
    }
}
