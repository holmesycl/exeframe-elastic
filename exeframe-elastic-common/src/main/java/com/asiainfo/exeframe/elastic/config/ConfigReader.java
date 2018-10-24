package com.asiainfo.exeframe.elastic.config;

import lombok.Getter;

import java.net.URL;

public abstract class ConfigReader {

    @Getter
    private URL url;

    @Getter
    private ConfigRegistry configRegistry;

    public ConfigReader(URL url) {
        this.url = url;
        configRegistry = new ConfigRegistry();
    }

    public abstract void loadConfig();
}
