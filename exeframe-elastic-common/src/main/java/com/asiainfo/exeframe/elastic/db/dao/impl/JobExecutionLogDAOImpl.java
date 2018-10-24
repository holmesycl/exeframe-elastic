package com.asiainfo.exeframe.elastic.db.dao.impl;

import com.asiainfo.exeframe.elastic.db.bo.BOJobExecutionLogEngine;
import com.asiainfo.exeframe.elastic.db.dao.interfaces.IJobExecutionLogDAO;
import com.asiainfo.exeframe.elastic.db.ivalues.IBOJobExecutionLogValue;

public class JobExecutionLogDAOImpl implements IJobExecutionLogDAO {

    @Override
    public void saveJobExecutionLog(IBOJobExecutionLogValue jobExecutionLogValue) throws Exception {
        BOJobExecutionLogEngine.save(jobExecutionLogValue);
    }

    @Override
    public IBOJobExecutionLogValue selectById(String id) throws Exception {
        return BOJobExecutionLogEngine.getBean(id);
    }
}
