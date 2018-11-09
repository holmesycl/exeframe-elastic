package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.JobConfig;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;
import io.elasticjob.lite.executor.handler.ExecutorServiceHandlerRegistry;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Log4j
public abstract class AbstractElasticJob<T, V> implements DataflowJob<T> {

    @Override
    public List<T> fetchData(final ShardingContext shardingContext) {
        final JobConfig jobConfig = createJobConfig(shardingContext);
        final int shardingTotalCount = shardingContext.getShardingTotalCount();
        final int shardingItem = shardingContext.getShardingItem();
        final int fetchNmu = jobConfig.getFetchNmu();
        return createDataFetcher(createJobConfig(shardingContext)).fetchData(shardingTotalCount, shardingItem, fetchNmu);
    }

    @Override
    public void processData(final ShardingContext shardingContext, final List<T> items) {
        final JobConfig jobConfig = createJobConfig(shardingContext);
        final String executorServiceName = jobConfig.getProcessType().name().toLowerCase() + "-" + jobConfig.getJobName();
        ListeningExecutorService executorService = obtainExecutorService(executorServiceName);
        final CountDownLatch latch = new CountDownLatch(items.size());
        for (final T item : items) {
            Futures.addCallback(executorService.submit(new Callable<V>() {

                @Override
                public V call() throws Exception {
                    return createDataConsumer(jobConfig, item).process(item);
                }
            }), new FutureCallback<V>() {
                @Override
                public void onSuccess(V result) {
                    Statistics.getInstance().incrementAndGet(shardingContext.getJobName() + "-" + shardingContext.getShardingItem());
                    latch.countDown();
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("数据：" + item + "处理失败。失败原因：" + t);
                    Statistics.getInstance().incrementAndGet(shardingContext.getJobName() + "-" + shardingContext.getShardingItem());
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private JobConfig createJobConfig(ShardingContext shardingContext) {
        return GsonFactory.getGson().fromJson(shardingContext.getJobParameter(), jobConfigType());
    }

    private ListeningExecutorService obtainExecutorService(final String executorServiceName) {
        return (ListeningExecutorService) ExecutorServiceHandlerRegistry.obtain(executorServiceName);
    }

    protected abstract Class<? extends JobConfig> jobConfigType();

    protected abstract DataFetcher<T> createDataFetcher(JobConfig jobConfig);

    protected abstract DataConsumer<T, V> createDataConsumer(JobConfig jobConfig, T item);

}
