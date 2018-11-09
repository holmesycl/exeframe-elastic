package com.asiainfo.exeframe.elastic.db.dao.impl;

import com.asiainfo.exeframe.elastic.db.bo.BOJobStatusTraceLogEngine;
import com.asiainfo.exeframe.elastic.db.dao.interfaces.IJobStatusTraceLogDAO;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobStatusTraceLogValue;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class JobStatusTraceLogDAOImpl implements IJobStatusTraceLogDAO {

    @Override
    public void insertJobStatusTraceLog(IBOJobStatusTraceLogValue jobStatusTraceLog) throws Exception {
        BOJobStatusTraceLogEngine.save(jobStatusTraceLog);
    }

    @Override
    public IBOJobStatusTraceLogValue[] selectByTaskIdAndState(String taskId, String state) throws Exception {
        StringBuilder condition = new StringBuilder("1 = 1");
        Map<String, Object> parameter = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(taskId)) {
            condition.append(" AND ").append(IBOJobStatusTraceLogValue.S_TaskId).append(" = ").append(IBOJobStatusTraceLogValue.S_TaskId);
            parameter.put(IBOJobStatusTraceLogValue.S_TaskId, taskId);
        }
        if (StringUtils.isNotBlank(state)) {
            condition.append(" AND ").append(IBOJobStatusTraceLogValue.S_State).append(" = ").append(IBOJobStatusTraceLogValue.S_State);
            parameter.put(IBOJobStatusTraceLogValue.S_State, state);
        }
        return BOJobStatusTraceLogEngine.getBeans(condition.toString(), parameter);
    }

}
