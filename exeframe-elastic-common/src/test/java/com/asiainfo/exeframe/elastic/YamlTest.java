package com.asiainfo.exeframe.elastic;

import com.google.common.io.Resources;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class YamlTest {

    @Test
    public void testLoadWhoami(){
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResource("elastic-exeframe.yaml").openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterable<Object> all = yaml.loadAll(inputStream);
        for (Object item :
                all) {
            System.out.println(item.getClass());
            System.out.println(item);
        }
    }

}
