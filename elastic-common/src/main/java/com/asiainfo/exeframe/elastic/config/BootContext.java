package com.asiainfo.exeframe.elastic.config;

import com.google.common.io.Resources;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
public abstract class BootContext {

    private static final String TENANT_FILE = "elastic-batch.properties";

    private String tenant;

    private String zkConnectionString;

    @Getter
    private List<ElasticJobConfig> elasticJobConfigs = new ArrayList<ElasticJobConfig>();

    public void init() {
        URL url = Resources.getResource(TENANT_FILE);
        Reader br = null;
        try {
            br = new InputStreamReader(url.openStream(), "utf-8");
            Properties properties = new Properties();
            properties.load(br);
            this.tenant = properties.getProperty("tenant");
            this.zkConnectionString = properties.getProperty("zkConnectionString");
            this.elasticJobConfigs = loadConfig(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected abstract List<ElasticJobConfig> loadConfig(Properties properties);
}
