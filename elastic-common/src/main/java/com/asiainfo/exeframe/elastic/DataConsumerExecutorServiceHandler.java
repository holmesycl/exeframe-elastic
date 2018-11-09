package com.asiainfo.exeframe.elastic;

import io.elasticjob.lite.executor.handler.ExecutorServiceHandler;
import io.elasticjob.lite.util.concurrent.ExecutorServiceObject;

import java.util.concurrent.ExecutorService;

public class DataConsumerExecutorServiceHandler implements ExecutorServiceHandler {

    private int corePoolSize;

    private int maximumPoolSize;

    public DataConsumerExecutorServiceHandler() {
        this.corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
        this.maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    }

    public DataConsumerExecutorServiceHandler(int corePoolSize, int maximumPoolSize) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
    }

    @Override
    public ExecutorService createExecutorService(String executorServiceName) {
        return new ExecutorServiceObject("process-job-" + executorServiceName, corePoolSize, maximumPoolSize).createExecutorService();
    }
}
