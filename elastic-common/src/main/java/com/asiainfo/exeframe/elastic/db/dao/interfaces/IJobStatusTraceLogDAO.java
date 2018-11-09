package com.asiainfo.exeframe.elastic.db.dao.interfaces;

import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobStatusTraceLogValue;

public interface IJobStatusTraceLogDAO {

    void insertJobStatusTraceLog(IBOJobStatusTraceLogValue jobStatusTraceLog) throws Exception;

    IBOJobStatusTraceLogValue[] selectByTaskIdAndState(String taskId, String state) throws Exception;
}
