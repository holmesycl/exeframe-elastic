package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.config.NameSpace;
import org.junit.Assert;
import org.junit.Test;

public class NameSpaceTest {

    @Test
    public void testName() {
        NameSpace nameSpace = new NameSpace("ord", ProcessType.VM);
        Assert.assertEquals("elastic-ord-vm", nameSpace.name());
    }

}
