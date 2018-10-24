package com.asiainfo.exeframe.elastic.db;

import io.elasticjob.lite.event.JobEventIdentity;

public class JobEventDbIdentity implements JobEventIdentity {
    /**
     * 获取作业事件标识.
     *
     * @return 作业事件标识
     */
    @Override
    public String getIdentity() {
        return "db";
    }
}
