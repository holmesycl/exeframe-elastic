package com.asiainfo.exeframe.elastic.db.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.*;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobStatusTraceLogValue;

import java.sql.Timestamp;

public class BOJobStatusTraceLogBean extends DataContainer implements DataContainerInterface, IBOJobStatusTraceLogValue {

    private static String m_boName = "com.asiainfo.exeframe.elastic.event.db.bo.BOJobStatusTraceLog";


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

    public static ObjectType S_TYPE = null;

    static {
        try {
            S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BOJobStatusTraceLogBean() throws AIException {
        super(S_TYPE);
    }

    public static ObjectType getObjectTypeStatic() throws AIException {
        return S_TYPE;
    }

    public void setObjectType(ObjectType value) throws AIException {
        //此种数据容器不能重置业务对象类型
        throw new AIException("Cannot reset ObjectType");
    }


    public void initMessage(String value) {
        this.initProperty(S_Message, value);
    }

    public void setMessage(String value) {
        this.set(S_Message, value);
    }

    public void setMessageNull() {
        this.set(S_Message, null);
    }

    public String getMessage() {
        return DataType.getAsString(this.get(S_Message));

    }

    public String getMessageInitialValue() {
        return DataType.getAsString(this.getOldObj(S_Message));
    }

    public void initSlaveId(String value) {
        this.initProperty(S_SlaveId, value);
    }

    public void setSlaveId(String value) {
        this.set(S_SlaveId, value);
    }

    public void setSlaveIdNull() {
        this.set(S_SlaveId, null);
    }

    public String getSlaveId() {
        return DataType.getAsString(this.get(S_SlaveId));

    }

    public String getSlaveIdInitialValue() {
        return DataType.getAsString(this.getOldObj(S_SlaveId));
    }

    public void initExecutionType(String value) {
        this.initProperty(S_ExecutionType, value);
    }

    public void setExecutionType(String value) {
        this.set(S_ExecutionType, value);
    }

    public void setExecutionTypeNull() {
        this.set(S_ExecutionType, null);
    }

    public String getExecutionType() {
        return DataType.getAsString(this.get(S_ExecutionType));

    }

    public String getExecutionTypeInitialValue() {
        return DataType.getAsString(this.getOldObj(S_ExecutionType));
    }

    public void initCreationTime(Timestamp value) {
        this.initProperty(S_CreationTime, value);
    }

    public void setCreationTime(Timestamp value) {
        this.set(S_CreationTime, value);
    }

    public void setCreationTimeNull() {
        this.set(S_CreationTime, null);
    }

    public Timestamp getCreationTime() {
        return DataType.getAsDateTime(this.get(S_CreationTime));

    }

    public Timestamp getCreationTimeInitialValue() {
        return DataType.getAsDateTime(this.getOldObj(S_CreationTime));
    }

    public void initSource(String value) {
        this.initProperty(S_Source, value);
    }

    public void setSource(String value) {
        this.set(S_Source, value);
    }

    public void setSourceNull() {
        this.set(S_Source, null);
    }

    public String getSource() {
        return DataType.getAsString(this.get(S_Source));

    }

    public String getSourceInitialValue() {
        return DataType.getAsString(this.getOldObj(S_Source));
    }

    public void initShardingItem(String value) {
        this.initProperty(S_ShardingItem, value);
    }

    public void setShardingItem(String value) {
        this.set(S_ShardingItem, value);
    }

    public void setShardingItemNull() {
        this.set(S_ShardingItem, null);
    }

    public String getShardingItem() {
        return DataType.getAsString(this.get(S_ShardingItem));

    }

    public String getShardingItemInitialValue() {
        return DataType.getAsString(this.getOldObj(S_ShardingItem));
    }

    public void initState(String value) {
        this.initProperty(S_State, value);
    }

    public void setState(String value) {
        this.set(S_State, value);
    }

    public void setStateNull() {
        this.set(S_State, null);
    }

    public String getState() {
        return DataType.getAsString(this.get(S_State));

    }

    public String getStateInitialValue() {
        return DataType.getAsString(this.getOldObj(S_State));
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

    public void initOriginalTaskId(String value) {
        this.initProperty(S_OriginalTaskId, value);
    }

    public void setOriginalTaskId(String value) {
        this.set(S_OriginalTaskId, value);
    }

    public void setOriginalTaskIdNull() {
        this.set(S_OriginalTaskId, null);
    }

    public String getOriginalTaskId() {
        return DataType.getAsString(this.get(S_OriginalTaskId));

    }

    public String getOriginalTaskIdInitialValue() {
        return DataType.getAsString(this.getOldObj(S_OriginalTaskId));
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

