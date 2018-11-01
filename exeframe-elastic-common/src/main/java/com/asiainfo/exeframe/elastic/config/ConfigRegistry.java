package com.asiainfo.exeframe.elastic.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConfigRegistry {

    private String whoami;

    private String defaultZkconnectString;

    private List<JobConfig> processDefinitions;

    public ConfigRegistry() {
        processDefinitions = new ArrayList<JobConfig>();
    }

}
