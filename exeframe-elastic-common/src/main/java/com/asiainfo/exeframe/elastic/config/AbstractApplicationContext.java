package com.asiainfo.exeframe.elastic.config;

import com.asiainfo.exeframe.elastic.config.reader.YamlReader;
import com.google.common.io.Resources;
import lombok.Getter;

import java.net.URL;
import java.util.List;

public abstract class AbstractApplicationContext {

    @Getter
    private String whoami;

    @Getter
    private String defaultZkconnectString;

    public AbstractApplicationContext(String location) {
        URL url = Resources.getResource(location);
        YamlReader yamlReader = new YamlReader(url);
        yamlReader.loadConfig();
        ConfigRegistry configRegistry = yamlReader.getConfigRegistry();
        this.whoami = configRegistry.getWhoami();
        this.defaultZkconnectString = configRegistry.getDefaultZkconnectString();
        initProcessDefinition(configRegistry.getProcessDefinitions());
    }

    public abstract void initProcessDefinition(List<ProcessDefinition> processDefinitions);

}
