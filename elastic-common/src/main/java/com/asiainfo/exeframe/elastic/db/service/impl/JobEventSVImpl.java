package com.asiainfo.exeframe.elastic.db.service.impl;

import com.ai.appframe2.service.ServiceFactory;
import com.asiainfo.exeframe.elastic.db.bo.BOJobExecutionLogBean;
import com.asiainfo.exeframe.elastic.db.bo.BOJobStatusTraceLogBean;
import com.asiainfo.exeframe.elastic.db.dao.interfaces.IJobExecutionLogDAO;
import com.asiainfo.exeframe.elastic.db.dao.interfaces.IJobStatusTraceLogDAO;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobExecutionLogValue;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobStatusTraceLogValue;
import com.asiainfo.exeframe.elastic.db.service.interfaces.IJobEventSV;
import com.google.common.base.Strings;
import io.elasticjob.lite.event.type.JobExecutionEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.UUID;

public class JobEventSVImpl implements IJobEventSV {
    @Override
    public void addJobExecutionEvent(JobExecutionEvent jobExecutionEvent) throws Exception {
        if (null == jobExecutionEvent.getCompleteTime()) {
            insertJobExecutionEvent(jobExecutionEvent);
        } else {
            updateJobExecutionEvent(jobExecutionEvent);
        }
    }

    private void updateJobExecutionEvent(JobExecutionEvent jobExecutionEvent) throws Exception {
        IJobExecutionLogDAO jobExecutionLogDAO = (IJobExecutionLogDAO) ServiceFactory.getService(IJobExecutionLogDAO.class);
        IBOJobExecutionLogValue jobExecutionLog = jobExecutionLogDAO.selectById(jobExecutionEvent.getId());
        jobExecutionLog.setIsSuccess(jobExecutionEvent.isSuccess() ? 1 : 0);
        jobExecutionLog.setCompleteTime(new Timestamp(jobExecutionEvent.getCompleteTime().getTime()));
        if (!jobExecutionEvent.isSuccess()) {
            jobExecutionLog.setFailureCause(jobExecutionEvent.getFailureCause());
        }
        jobExecutionLogDAO.saveJobExecutionLog(jobExecutionLog);
    }

    private void insertJobExecutionEvent(JobExecutionEvent jobExecutionEvent) throws Exception {
        IBOJobExecutionLogValue jobExecutionLog = new BOJobExecutionLogBean();
        jobExecutionLog.setId(jobExecutionEvent.getId());
        jobExecutionLog.setJobName(jobExecutionEvent.getJobName());
        jobExecutionLog.setTaskId(jobExecutionEvent.getTaskId());
        jobExecutionLog.setHostname(jobExecutionEvent.getHostname());
        jobExecutionLog.setIp(jobExecutionEvent.getIp());
        jobExecutionLog.setShardingItem(jobExecutionEvent.getShardingItem());
        jobExecutionLog.setExecutionSource(jobExecutionEvent.getSource().toString());
        jobExecutionLog.setIsSuccess(jobExecutionEvent.isSuccess() ? 1 : 0);
        jobExecutionLog.setStartTime(new Timestamp(jobExecutionEvent.getStartTime().getTime()));
        IJobExecutionLogDAO jobExecutionLogDAO = (IJobExecutionLogDAO) ServiceFactory.getService(IJobExecutionLogDAO.class);
        jobExecutionLogDAO.saveJobExecutionLog(jobExecutionLog);
    }

    @Override
    public void addJobStatusTraceEvent(JobStatusTraceEvent jobStatusTraceEvent) throws Exception {
        String originalTaskId = jobStatusTraceEvent.getOriginalTaskId();
        if (JobStatusTraceEvent.State.TASK_STAGING != jobStatusTraceEvent.getState()) {
            originalTaskId = getOriginalTaskId(jobStatusTraceEvent.getTaskId());
        }
        if (StringUtils.isBlank(originalTaskId)) {
            originalTaskId = "-";
        }
        IBOJobStatusTraceLogValue jobStatusTraceLog = new BOJobStatusTraceLogBean();
        jobStatusTraceLog.setId(UUID.randomUUID().toString());
        jobStatusTraceLog.setJobName(jobStatusTraceEvent.getJobName());
        jobStatusTraceLog.setOriginalTaskId(originalTaskId);
        jobStatusTraceLog.setTaskId(jobStatusTraceEvent.getTaskId());
        jobStatusTraceLog.setSlaveId(jobStatusTraceEvent.getSlaveId());
        jobStatusTraceLog.setSource(jobStatusTraceEvent.getSource().toString());
        jobStatusTraceLog.setExecutionType(jobStatusTraceEvent.getExecutionType().name());
        jobStatusTraceLog.setShardingItem(jobStatusTraceEvent.getShardingItems());
        jobStatusTraceLog.setState(jobStatusTraceEvent.getState().toString());
        jobStatusTraceLog.setMessage(truncateString(jobStatusTraceEvent.getMessage()));
        jobStatusTraceLog.setCreationTime(new Timestamp(jobStatusTraceEvent.getCreationTime().getTime()));
        IJobStatusTraceLogDAO jobStatusTraceLogDAO = (IJobStatusTraceLogDAO) ServiceFactory.getService(IJobStatusTraceLogDAO.class);
        jobStatusTraceLogDAO.insertJobStatusTraceLog(jobStatusTraceLog);
    }

    private String truncateString(final String str) {
        return !Strings.isNullOrEmpty(str) && str.length() > 4000 ? str.substring(0, 4000) : str;
    }

    private String getOriginalTaskId(String taskId) throws Exception {
        IJobStatusTraceLogDAO jobStatusTraceLogDAO = (IJobStatusTraceLogDAO) ServiceFactory.getService(IJobStatusTraceLogDAO.class);
        IBOJobStatusTraceLogValue[] jobStatusTraceLogValues = jobStatusTraceLogDAO.selectByTaskIdAndState(taskId, JobStatusTraceEvent.State.TASK_STAGING.name());
        String originalTaskId = "";
        if (jobStatusTraceLogValues != null && jobStatusTraceLogValues.length >= 1) {
            originalTaskId = jobStatusTraceLogValues[0].getOriginalTaskId();
        }
        return originalTaskId;
    }
}
