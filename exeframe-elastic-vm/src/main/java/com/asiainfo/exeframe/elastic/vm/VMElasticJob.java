package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.asiainfo.exeframe.elastic.AbstractElasticJob;
import com.asiainfo.exeframe.elastic.DataConsumer;
import com.asiainfo.exeframe.elastic.DataFetcher;
import com.asiainfo.exeframe.elastic.config.vm.VMElasticJobConfig;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VMElasticJob extends AbstractElasticJob<IBOVmScheduleValue> {

    @Override
    protected DataConsumer<IBOVmScheduleValue> createDataConsumer(ShardingContext shardingContext) {
        VMElasticJobConfig jobConfig = createJobConfig(shardingContext);
        return new VMDataConsumerFactory(jobConfig.getQueueType()).create();
    }

    private VMElasticJobConfig createJobConfig(ShardingContext shardingContext) {
        return GsonFactory.getGson().fromJson(shardingContext.getJobParameter(), VMElasticJobConfig.class);
    }

    @Override
    protected DataFetcher<IBOVmScheduleValue> createDataFetcher(ShardingContext shardingContext) {
        VMElasticJobConfig jobConfig = createJobConfig(shardingContext);
        return new VMDataFetcherFactory(jobConfig.getQueueId(), jobConfig.getQueueType()).create();
    }

}
