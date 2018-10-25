package com.asiainfo.exeframe.elastic.config;

import com.asiainfo.exeframe.elastic.config.reader.YamlReader;
import com.google.common.io.Resources;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.URL;
import java.util.List;

public abstract class AbstractApplicationContext {

    @Getter
    private String whoami;

    @Getter
    private String defaultZkconnectString;

    @Getter(value = AccessLevel.PROTECTED)
    private List<ProcessDefinition> processDefinitions;

    public AbstractApplicationContext(String fileName) {
        URL url = Resources.getResource(fileName);
        YamlReader yamlReader = new YamlReader(url);
        yamlReader.loadConfig();
        this.whoami = yamlReader.getConfigRegistry().getWhoami();
        this.defaultZkconnectString = yamlReader.getConfigRegistry().getDefaultZkconnectString();
        this.processDefinitions = yamlReader.getConfigRegistry().getProcessDefinitions();
    }
}
