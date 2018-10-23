package com.asiainfo.exeframe.elastic.db.dao.interfaces;

import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobExecutionLogValue;

public interface IJobExecutionLogDAO {

    void saveJobExecutionLog(IBOJobExecutionLogValue jobExecutionLogValue) throws Exception;

    IBOJobExecutionLogValue selectById(String id) throws Exception;
}
