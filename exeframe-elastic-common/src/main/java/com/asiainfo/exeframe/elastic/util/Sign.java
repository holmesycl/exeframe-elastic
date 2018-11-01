package com.asiainfo.exeframe.elastic.util;

public enum Sign {

    SLASH("/"), DOT("."), COMMA(",");

    private String mark;

    Sign(String mark) {
        this.mark = mark;
    }

    public String mark() {
        return this.mark;
    }

}
