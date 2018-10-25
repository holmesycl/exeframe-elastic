package com.asiainfo.exeframe.elastic;

import lombok.Getter;

public class ElasticProcessFrameWork {
    @Getter
    private static String configFile = "elastic-exeframe.yaml";
    @Getter
    private static String[] bootProcessCodes;

    protected static void initBootParam(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-config".equals(args[i])) {
                configFile = args[i + 1];
                i++;
            } else if ("-codes".equals(args[i])) {
                String codes = args[i + 1];
                bootProcessCodes = codes.split(",");
                i++;
            }
        }
    }

}
