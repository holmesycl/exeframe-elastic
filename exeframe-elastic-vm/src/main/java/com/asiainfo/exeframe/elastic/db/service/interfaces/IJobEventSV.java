package com.asiainfo.exeframe.elastic.db.service.interfaces;

import io.elasticjob.lite.event.type.JobExecutionEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent;

public interface IJobEventSV {

    void addJobExecutionEvent(JobExecutionEvent jobExecutionEvent) throws Exception;

    void addJobStatusTraceEvent(JobStatusTraceEvent jobStatusTraceEvent) throws Exception;

}
