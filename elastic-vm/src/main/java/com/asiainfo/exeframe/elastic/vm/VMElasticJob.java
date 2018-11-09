package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.IQueueProcessor;
import com.ai.comframe.utils.DataSourceUtil;
import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.asiainfo.exeframe.elastic.AbstractElasticJob;
import com.asiainfo.exeframe.elastic.DataConsumer;
import com.asiainfo.exeframe.elastic.DataFetcher;
import com.asiainfo.exeframe.elastic.config.JobConfig;
import com.asiainfo.exeframe.elastic.vm.config.VMElasticJobConfig;
import lombok.extern.log4j.Log4j;

@Log4j
public class VMElasticJob extends AbstractElasticJob<IBOVmScheduleValue, Boolean> {

    @Override
    protected Class<? extends JobConfig> jobConfigType() {
        return VMElasticJobConfig.class;
    }

    @Override
    protected DataFetcher<IBOVmScheduleValue> createDataFetcher(JobConfig jobConfig) {
        return createVMDataFetcher((VMElasticJobConfig) jobConfig);
    }

    private DataFetcher createVMDataFetcher(final VMElasticJobConfig vmElasticJobConfig) {
        return VMDataFetchFactory.create(vmElasticJobConfig);
    }

    @Override
    protected DataConsumer<IBOVmScheduleValue, Boolean> createDataConsumer(JobConfig jobConfig, final IBOVmScheduleValue item) {
        final VMElasticJobConfig vmElasticJobConfig = (VMElasticJobConfig) jobConfig;
        return new DataConsumer<IBOVmScheduleValue, Boolean>() {
            @Override
            public Boolean process(IBOVmScheduleValue data) {
                try {
                    DataSourceUtil.pushDataSourcebyQueueId(vmElasticJobConfig.getQueueId());
                    IQueueProcessor queueProcessor = QueueProcessorFactory.getQueueProcessor(vmElasticJobConfig.getQueueType());
                    return queueProcessor.execute(data);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        DataSourceUtil.popDataSource();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }
}
