package com.asiainfo.exeframe.elastic;

import com.google.common.util.concurrent.*;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;
import io.elasticjob.lite.executor.StreamingProcessFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AbstractElasticJob<T, V> implements DataflowJob<T>, StreamingProcessFilter {

    private ListeningExecutorService executorService;

    public abstract List<T> fetchData(ShardingContext shardingContext);

    /**
     * 开始处理前执行
     *
     * @param shardingContext
     * @param item
     */
    public void beforeProcessData(ShardingContext shardingContext, T item) throws Throwable {

    }

    /**
     * 处理单条数据
     *
     * @param shardingContext
     * @param item
     * @return
     */
    public abstract V processData(ShardingContext shardingContext, T item) throws Throwable;

    /**
     * 数据处理异常时执行
     *
     * @param shardingContext
     * @param item
     * @param t
     */
    public void exceptionProcessData(ShardingContext shardingContext, T item, Throwable t) {
        log.error(shardingContext.toString() + " - " + item.toString(), t);
    }

    /**
     * 数据处理成功时执行
     *
     * @param shardingContext
     * @param item
     */
    public void afterProcessData(ShardingContext shardingContext, T item) {

    }

    /**
     * 处理返回时执行
     *
     * @param shardingContext
     * @param item
     */
    public void returnProcessData(ShardingContext shardingContext, T item) {

    }

    @Override
    public void processData(final ShardingContext shardingContext, List<T> items) {
        final CountDownLatch latch = new CountDownLatch(items.size());
        for (final T item : items) {
            ListenableFuture<V> explosion = executorService.submit(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    V v = null;
                    try {
                        beforeProcessData(shardingContext, item);
                        v = processData(shardingContext, item);
                        afterProcessData(shardingContext, item);
                    } catch (Throwable t) {
                        exceptionProcessData(shardingContext, item, t);
                    } finally {
                        returnProcessData(shardingContext, item);
                    }
                    return v;
                }
            });
            Futures.addCallback(explosion, new FutureCallback<V>() {
                @Override
                public void onSuccess(V result) {
                    latch.countDown();
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("任务处理失败。", t);
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

    @Override
    public void beforeStreamingProcess(ShardingContext shardingContext) {
        int threads = Runtime.getRuntime().availableProcessors() + 1;
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threads));
    }

    @Override
    public void afterStreamingProcess(ShardingContext shardingContext, int processDataSize) {
        executorService.shutdown();
    }
}
