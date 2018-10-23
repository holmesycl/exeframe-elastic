package com.asiainfo.exeframe.elastic.db;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.exeframe.elastic.db.service.interfaces.IJobEventSV;
import io.elasticjob.lite.event.JobEventListener;
import io.elasticjob.lite.event.type.JobExecutionEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobEventDbListener extends JobEventDbIdentity implements JobEventListener {
    /**
     * 作业执行事件监听执行.
     *
     * @param jobExecutionEvent 作业执行事件
     */
    @Override
    public void listen(JobExecutionEvent jobExecutionEvent) {
        IJobEventSV jobEventService = (IJobEventSV) ServiceFactory.getService(IJobEventSV.class);
        try {
            jobEventService.addJobExecutionEvent(jobExecutionEvent);
        } catch (Exception e) {
            log.error("addJobExecutionEvent error.", e);
        }
    }

    /**
     * 作业状态痕迹事件监听执行.
     *
     * @param jobStatusTraceEvent 作业状态痕迹事件
     */
    @Override
    public void listen(JobStatusTraceEvent jobStatusTraceEvent) {
        IJobEventSV jobEventService = (IJobEventSV) ServiceFactory.getService(IJobEventSV.class);
        try {
            jobEventService.addJobStatusTraceEvent(jobStatusTraceEvent);
        } catch (Exception e) {
            log.error("addJobStatusTraceEvent error.", e);
        }
    }
}
