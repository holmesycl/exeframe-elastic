package com.asiainfo.exeframe.elastic.config;

import com.google.common.io.Resources;
import lombok.Data;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Properties;

@Data
public abstract class BootContext {

    private static final String TENANT_FILE = "exeframe.properties";

    private String tenant;

    private String zkConnectionString;

    public abstract List<ElasticJobConfig> getElasticJobConfigs();

    public void init() {
        URL url = Resources.getResource(TENANT_FILE);
        Reader br = null;
        try {
            br = new InputStreamReader(url.openStream(), "utf-8");
            Properties properties = new Properties();
            properties.load(br);
            this.tenant = properties.getProperty("tenant");
            this.zkConnectionString = properties.getProperty("zkConnectionString");
            loadConfig(properties);
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

    protected abstract void loadConfig(Properties properties);
}
