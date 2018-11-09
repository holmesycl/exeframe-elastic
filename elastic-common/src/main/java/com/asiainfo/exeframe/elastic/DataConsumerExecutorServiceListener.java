package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.JobConfig;
import io.elasticjob.lite.api.listener.ElasticJobListener;
import io.elasticjob.lite.executor.ShardingContexts;
import io.elasticjob.lite.executor.handler.ExecutorServiceHandlerRegistry;
import io.elasticjob.lite.util.json.GsonFactory;

public class DataConsumerExecutorServiceListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        final String executorServiceName = getExecutorServiceName(shardingContexts);
        final int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        final int maximumPoolSize = corePoolSize * shardingContexts.getShardingItemParameters().keySet().size();
        ExecutorServiceHandlerRegistry.getExecutorServiceHandler(executorServiceName, new DataConsumerExecutorServiceHandler(corePoolSize, maximumPoolSize));
    }

    private String getExecutorServiceName(final ShardingContexts shardingContexts) {
        final JobConfig jobConfig = GsonFactory.getGson().fromJson(shardingContexts.getJobParameter(), JobConfig.class);
        return jobConfig.getProcessType().name().toLowerCase() + "-" + jobConfig.getJobName();
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        final String executorServiceName = getExecutorServiceName(shardingContexts);
        ExecutorServiceHandlerRegistry.obtain(executorServiceName).shutdown();
        ExecutorServiceHandlerRegistry.remove(executorServiceName);
    }
}
