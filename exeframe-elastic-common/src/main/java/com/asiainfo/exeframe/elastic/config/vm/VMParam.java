package com.asiainfo.exeframe.elastic.config.vm;

import lombok.Data;

@Data
public class VMParam {

    private String queueId;
    private String queueType;
    private int intervalSecond = 5;
    private int shardingTotalCount = 10;
    private int fetchNum = 100;

}
