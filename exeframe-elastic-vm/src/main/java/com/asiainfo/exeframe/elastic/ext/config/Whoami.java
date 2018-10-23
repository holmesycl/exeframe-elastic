package com.asiainfo.exeframe.elastic.ext.config;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Whoami {

    private static Whoami ourInstance = new Whoami();

    public static Whoami getInstance() {
        return ourInstance;
    }

    private static final String CONFIG_FILE_NAME_DEFAULT = "exeframe.properties";
    private static final String CONFIG_FILE_NAME_EXT = "exeframe-ext.properties";

    private final String i;

    private Whoami() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResource(CONFIG_FILE_NAME_DEFAULT).openStream();
        } catch (Exception e) {
            log.error("未发现配置文件" + CONFIG_FILE_NAME_DEFAULT);
            log.error("开始加载配置文件" + CONFIG_FILE_NAME_EXT);
            try {
                inputStream = Resources.getResource(CONFIG_FILE_NAME_EXT).openStream();
            } catch (Exception ex) {
                throw new RuntimeException("配置文件" + CONFIG_FILE_NAME_EXT + "加载失败。", ex);
            }
        }
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        i = (String) properties.get("whoami");
        if (null != inputStream) {
            try {
                inputStream.close();
            } catch (Exception e) {
                //
                log.error("close inputStream error!", e);
            }
        }
    }

    public String get() {
        return i;
    }

}
