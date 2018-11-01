package com.asiainfo.exeframe.elastic.config;

import lombok.Data;

@Data
public class ElasticJobConfig extends JobConfig {

    public static final String ZK_CONNECTION_STRING = "zkConnectionString";
    public static final String SHARDING_TO_TOTAL_COUNT = "shardingTotalCount";

    private String jobParameter;
    private String zkConnectionString;
    private int shardingTotalCount = 10;
}
