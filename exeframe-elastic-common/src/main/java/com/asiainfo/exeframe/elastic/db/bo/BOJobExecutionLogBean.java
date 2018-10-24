package com.asiainfo.exeframe.elastic.db.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobExecutionLogValue;

import java.sql.Timestamp;

public class BOJobExecutionLogBean extends DataContainer implements DataContainerInterface, IBOJobExecutionLogValue {

    private static String m_boName = "com.asiainfo.exeframe.elastic.event.db.bo.BOJobExecutionLog";


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

    public static ObjectType S_TYPE = null;

    static {
        try {
            S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BOJobExecutionLogBean() throws AIException {
        super(S_TYPE);
    }

    public static ObjectType getObjectTypeStatic() throws AIException {
        return S_TYPE;
    }

    public void setObjectType(ObjectType value) throws AIException {
        throw new AIException("Cannot reset ObjectType");
    }


    public void initFailureCause(String value) {
        this.initProperty(S_FailureCause, value);
    }

    public void setFailureCause(String value) {
        this.set(S_FailureCause, value);
    }

    public void setFailureCauseNull() {
        this.set(S_FailureCause, null);
    }

    public String getFailureCause() {
        return DataType.getAsString(this.get(S_FailureCause));

    }

    public String getFailureCauseInitialValue() {
        return DataType.getAsString(this.getOldObj(S_FailureCause));
    }

    public void initStartTime(Timestamp value) {
        this.initProperty(S_StartTime, value);
    }

    public void setStartTime(Timestamp value) {
        this.set(S_StartTime, value);
    }

    public void setStartTimeNull() {
        this.set(S_StartTime, null);
    }

    public Timestamp getStartTime() {
        return DataType.getAsDateTime(this.get(S_StartTime));

    }

    public Timestamp getStartTimeInitialValue() {
        return DataType.getAsDateTime(this.getOldObj(S_StartTime));
    }

    public void initCompleteTime(Timestamp value) {
        this.initProperty(S_CompleteTime, value);
    }

    public void setCompleteTime(Timestamp value) {
        this.set(S_CompleteTime, value);
    }

    public void setCompleteTimeNull() {
        this.set(S_CompleteTime, null);
    }

    public Timestamp getCompleteTime() {
        return DataType.getAsDateTime(this.get(S_CompleteTime));

    }

    public Timestamp getCompleteTimeInitialValue() {
        return DataType.getAsDateTime(this.getOldObj(S_CompleteTime));
    }

    public void initHostname(String value) {
        this.initProperty(S_Hostname, value);
    }

    public void setHostname(String value) {
        this.set(S_Hostname, value);
    }

    public void setHostnameNull() {
        this.set(S_Hostname, null);
    }

    public String getHostname() {
        return DataType.getAsString(this.get(S_Hostname));

    }

    public String getHostnameInitialValue() {
        return DataType.getAsString(this.getOldObj(S_Hostname));
    }

    public void initExecutionSource(String value) {
        this.initProperty(S_ExecutionSource, value);
    }

    public void setExecutionSource(String value) {
        this.set(S_ExecutionSource, value);
    }

    public void setExecutionSourceNull() {
        this.set(S_ExecutionSource, null);
    }

    public String getExecutionSource() {
        return DataType.getAsString(this.get(S_ExecutionSource));

    }

    public String getExecutionSourceInitialValue() {
        return DataType.getAsString(this.getOldObj(S_ExecutionSource));
    }

    public void initIp(String value) {
        this.initProperty(S_Ip, value);
    }

    public void setIp(String value) {
        this.set(S_Ip, value);
    }

    public void setIpNull() {
        this.set(S_Ip, null);
    }

    public String getIp() {
        return DataType.getAsString(this.get(S_Ip));

    }

    public String getIpInitialValue() {
        return DataType.getAsString(this.getOldObj(S_Ip));
    }

    public void initShardingItem(long value) {
        this.initProperty(S_ShardingItem, new Long(value));
    }

    public void setShardingItem(long value) {
        this.set(S_ShardingItem, new Long(value));
    }

    public void setShardingItemNull() {
        this.set(S_ShardingItem, null);
    }

    public long getShardingItem() {
        return DataType.getAsLong(this.get(S_ShardingItem));

    }

    public long getShardingItemInitialValue() {
        return DataType.getAsLong(this.getOldObj(S_ShardingItem));
    }

    public void initId(String value) {
        this.initProperty(S_Id, value);
    }

    public void setId(String value) {
        this.set(S_Id, value);
    }

    public void setIdNull() {
        this.set(S_Id, null);
    }

    public String getId() {
        return DataType.getAsString(this.get(S_Id));

    }

    public String getIdInitialValue() {
        return DataType.getAsString(this.getOldObj(S_Id));
    }

    public void initTaskId(String value) {
        this.initProperty(S_TaskId, value);
    }

    public void setTaskId(String value) {
        this.set(S_TaskId, value);
    }

    public void setTaskIdNull() {
        this.set(S_TaskId, null);
    }

    public String getTaskId() {
        return DataType.getAsString(this.get(S_TaskId));

    }

    public String getTaskIdInitialValue() {
        return DataType.getAsString(this.getOldObj(S_TaskId));
    }

    public void initIsSuccess(long value) {
        this.initProperty(S_IsSuccess, new Long(value));
    }

    public void setIsSuccess(long value) {
        this.set(S_IsSuccess, new Long(value));
    }

    public void setIsSuccessNull() {
        this.set(S_IsSuccess, null);
    }

    public long getIsSuccess() {
        return DataType.getAsLong(this.get(S_IsSuccess));

    }

    public long getIsSuccessInitialValue() {
        return DataType.getAsLong(this.getOldObj(S_IsSuccess));
    }

    public void initJobName(String value) {
        this.initProperty(S_JobName, value);
    }

    public void setJobName(String value) {
        this.set(S_JobName, value);
    }

    public void setJobNameNull() {
        this.set(S_JobName, null);
    }

    public String getJobName() {
        return DataType.getAsString(this.get(S_JobName));

    }

    public String getJobNameInitialValue() {
        return DataType.getAsString(this.getOldObj(S_JobName));
    }


}
