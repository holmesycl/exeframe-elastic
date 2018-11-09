package com.asiainfo.exeframe.elastic.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NameSpaceTest {

    @Test
    public void testPath() {
        NameSpace nameSpace = new NameSpace("ord", ProcessType.VM);
        assertEquals("elastic/ord/vm", nameSpace.path());
    }
}