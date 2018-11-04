package com.asiainfo.exeframe.elastic;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;
import io.elasticjob.lite.executor.StreamingProcessFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstractElasticJob<T> implements DataflowJob<T>, StreamingProcessFilter {

    private ConcurrentLinkedQueue<T> dataQueue;

    private DataFetcher<T> dataFetcher;

    private AtomicBoolean allotJobNeedToEnd = new AtomicBoolean(false);

    private ListenableFuture<Integer> listenableFuture;


    @Override
    public List<T> fetchData(ShardingContext shardingContext) {
        return dataFetcher.fetchData(shardingContext);
    }

    @Override
    public void processData(final ShardingContext shardingContext, List<T> items) {
        this.dataQueue.addAll(items);
    }

    @Override
    public void beforeStreamingProcess(ShardingContext shardingContext) {
        this.dataQueue = new ConcurrentLinkedQueue<T>();
        this.dataFetcher = createDataFetcher(shardingContext);
        ListeningExecutorService allotExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.listenableFuture = allotExecutorService.submit(new AllotJobDataTask(dataQueue, createDataConsumer(shardingContext), allotJobNeedToEnd));
        allotExecutorService.shutdown();
    }

    protected abstract DataConsumer<T> createDataConsumer(ShardingContext shardingContext);

    protected abstract DataFetcher<T> createDataFetcher(ShardingContext shardingContext);

    @Override
    public void afterStreamingProcess(ShardingContext shardingContext, int processDataSize) {
        // 通知数据分配任务结束
        allotJobNeedToEnd.set(true);
        try {
            // 等待数据处理结束
            int dataSize = listenableFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
