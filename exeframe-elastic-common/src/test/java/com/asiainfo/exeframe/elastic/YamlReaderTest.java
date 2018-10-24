package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.reader.YamlReader;
import com.google.common.io.Resources;
import org.junit.Test;

import java.net.URL;

public class YamlReaderTest {

    @Test
    public void testLoadConfig() {
        URL url = Resources.getResource("elastic-exeframe.yaml");
        new YamlReader(url).loadConfig();
    }

}
