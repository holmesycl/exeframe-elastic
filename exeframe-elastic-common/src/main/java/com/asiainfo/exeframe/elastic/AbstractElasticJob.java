package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.JobConfig;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;
import io.elasticjob.lite.executor.StreamingProcessFilter;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AbstractElasticJob<T> implements DataflowJob<T>, StreamingProcessFilter {

    private ConcurrentLinkedQueue<T> dataQueue;

    private DataFetcher<T> dataFetcher;

    private DataConsumer<T> dataConsumer;

    @Getter
    private JobConfig jobConfig;

    private ListeningExecutorService allotExecutorService;

    private ListenableFuture listenableFuture;


    @Override
    public List<T> fetchData(ShardingContext shardingContext) {
        int shardingItem = shardingContext.getShardingItem();
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        int fetchNum = getJobConfig().getFetchNmu();
        // 等待数据处理完毕后再继续兜取新的数据
        // 需要抽象
        return dataFetcher.fetchData(shardingItem, shardingTotalCount, fetchNum);
    }

    @Override
    public void processData(final ShardingContext shardingContext, List<T> items) {
        this.dataQueue.addAll(items);
    }

    @Override
    public void beforeStreamingProcess(ShardingContext shardingContext) {
        this.jobConfig = GsonFactory.getGson().fromJson(shardingContext.getJobParameter(), jobConfigType());
        this.dataQueue = new ConcurrentLinkedQueue<T>();
        this.dataFetcher = createDataFetcher();
        this.dataConsumer = createDataConsumer();
        this.allotExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.listenableFuture = this.allotExecutorService.submit(new AllotJobDataTask(this.dataQueue));
    }

    protected abstract Class<? extends JobConfig> jobConfigType();

    protected abstract DataConsumer<T> createDataConsumer();

    protected abstract DataFetcher<T> createDataFetcher();


    @Override
    public void afterStreamingProcess(ShardingContext shardingContext, int processDataSize) {
        // 数据兜取结束
        // 清理现场
        this.allotExecutorService.shutdown();
    }
}
