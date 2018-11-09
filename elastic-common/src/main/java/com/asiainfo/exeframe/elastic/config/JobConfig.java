package com.asiainfo.exeframe.elastic.config;

import lombok.Data;

@Data
public class JobConfig {

    public static final String JOB_NAME = "jobName";
    public static final String FETCH_NUM = "fetchNmu";
    public static final String TIME_INTERVAL = "timeInterval";

    private String tenant;
    private String jobName;
    private ProcessType processType = ProcessType.VM;
    private int fetchNmu;
    private long timeInterval;
}
