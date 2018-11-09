package com.asiainfo.exeframe.elastic.db.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOJobExecutionLogValue extends DataStructInterface {

    public final static String S_FailureCause = "FAILURE_CAUSE";
    public final static String S_StartTime = "START_TIME";
    public final static String S_CompleteTime = "COMPLETE_TIME";
    public final static String S_Hostname = "HOSTNAME";
    public final static String S_ExecutionSource = "EXECUTION_SOURCE";
    public final static String S_Ip = "IP";
    public final static String S_ShardingItem = "SHARDING_ITEM";
    public final static String S_Id = "ID";
    public final static String S_TaskId = "TASK_ID";
    public final static String S_IsSuccess = "IS_SUCCESS";
    public final static String S_JobName = "JOB_NAME";


    public String getFailureCause();

    public Timestamp getStartTime();

    public Timestamp getCompleteTime();

    public String getHostname();

    public String getExecutionSource();

    public String getIp();

    public long getShardingItem();

    public String getId();

    public String getTaskId();

    public long getIsSuccess();

    public String getJobName();


    public void setFailureCause(String value);

    public void setStartTime(Timestamp value);

    public void setCompleteTime(Timestamp value);

    public void setHostname(String value);

    public void setExecutionSource(String value);

    public void setIp(String value);

    public void setShardingItem(long value);

    public void setId(String value);

    public void setTaskId(String value);

    public void setIsSuccess(long value);

    public void setJobName(String value);
}
