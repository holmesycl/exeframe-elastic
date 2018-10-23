package com.asiainfo.exeframe.elastic.db.ivalues;

import com.ai.appframe2.common.DataStructInterface;

import java.sql.Timestamp;

public interface IBOJobStatusTraceLogValue extends DataStructInterface {

    public final static String S_Message = "MESSAGE";
    public final static String S_SlaveId = "SLAVE_ID";
    public final static String S_ExecutionType = "EXECUTION_TYPE";
    public final static String S_CreationTime = "CREATION_TIME";
    public final static String S_Source = "SOURCE";
    public final static String S_ShardingItem = "SHARDING_ITEM";
    public final static String S_State = "STATE";
    public final static String S_Id = "ID";
    public final static String S_OriginalTaskId = "ORIGINAL_TASK_ID";
    public final static String S_TaskId = "TASK_ID";
    public final static String S_JobName = "JOB_NAME";


    public String getMessage();

    public String getSlaveId();

    public String getExecutionType();

    public Timestamp getCreationTime();

    public String getSource();

    public String getShardingItem();

    public String getState();

    public String getId();

    public String getOriginalTaskId();

    public String getTaskId();

    public String getJobName();


    public void setMessage(String value);

    public void setSlaveId(String value);

    public void setExecutionType(String value);

    public void setCreationTime(Timestamp value);

    public void setSource(String value);

    public void setShardingItem(String value);

    public void setState(String value);

    public void setId(String value);

    public void setOriginalTaskId(String value);

    public void setTaskId(String value);

    public void setJobName(String value);
}
