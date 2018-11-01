package com.asiainfo.exeframe.elastic.config.vm;

import com.asiainfo.exeframe.elastic.config.ElasticJobConfig;
import lombok.Data;

@Data
public class VMElasticJobConfig extends ElasticJobConfig {
    private String queueId;
    private String queueType;
}
