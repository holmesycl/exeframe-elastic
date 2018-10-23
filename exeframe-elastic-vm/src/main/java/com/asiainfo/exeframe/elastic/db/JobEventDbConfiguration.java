package com.asiainfo.exeframe.elastic.db;

import io.elasticjob.lite.event.JobEventConfiguration;
import io.elasticjob.lite.event.JobEventListener;
import io.elasticjob.lite.event.JobEventListenerConfigurationException;

public class JobEventDbConfiguration extends JobEventDbIdentity implements JobEventConfiguration {

    /**
     * 创建作业事件监听器.
     *
     * @return 作业事件监听器.
     * @throws JobEventListenerConfigurationException 作业事件监听器配置异常
     */
    @Override
    public JobEventListener createJobEventListener() throws JobEventListenerConfigurationException {
        return new JobEventDbListener();
    }
}
