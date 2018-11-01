package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.asiainfo.exeframe.elastic.AbstractElasticJob;
import com.asiainfo.exeframe.elastic.DataConsumer;
import com.asiainfo.exeframe.elastic.DataFetcher;
import com.asiainfo.exeframe.elastic.config.JobConfig;
import com.asiainfo.exeframe.elastic.config.vm.VMElasticJobConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VMElasticJob extends AbstractElasticJob<IBOVmScheduleValue> {

    @Override
    protected DataConsumer<IBOVmScheduleValue> createDataConsumer() {
        VMElasticJobConfig vmElasticJobConfig = (VMElasticJobConfig) getJobConfig();
        return new VMDataConsumerFactory(vmElasticJobConfig.getQueueType()).create();
    }

    @Override
    protected DataFetcher<IBOVmScheduleValue> createDataFetcher() {
        VMElasticJobConfig jobConfig = (VMElasticJobConfig) getJobConfig();
        return new VMDataFetcherFactory(jobConfig.getQueueId(), jobConfig.getQueueType()).create();
    }

    @Override
    protected Class<? extends JobConfig> jobConfigType() {
        return VMElasticJobConfig.class;
    }
}
