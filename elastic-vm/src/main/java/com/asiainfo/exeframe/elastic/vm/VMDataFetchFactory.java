package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.IQueueProcessor;
import com.ai.comframe.utils.DataSourceUtil;
import com.ai.comframe.vm.workflow.bo.BOVmScheduleBean;
import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.asiainfo.exeframe.elastic.DataFetcher;
import com.asiainfo.exeframe.elastic.vm.config.VMBootParam;
import com.asiainfo.exeframe.elastic.vm.config.VMElasticJobConfig;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VMDataFetchFactory {
    public static DataFetcher create(final VMElasticJobConfig vmElasticJobConfig) {
        final VMBootParam.QueueBootParam.FetchModel fetchModel = vmElasticJobConfig.getFetchModel();
        if (fetchModel == VMBootParam.QueueBootParam.FetchModel.RANDOM) {
            return new DataFetcher<IBOVmScheduleValue>() {
                @Override
                public List<IBOVmScheduleValue> fetchData(int shardingTotalCount, int shardingItem, int fetchNmu) {
                    try {
                        DataSourceUtil.pushDataSourcebyQueueId(vmElasticJobConfig.getQueueId());
                        IQueueProcessor queueProcessor = QueueProcessorFactory.getQueueProcessor(vmElasticJobConfig.getQueueType());
                        List<IBOVmScheduleValue> scheduleValues = Lists.newArrayList();
                        List<IBOVmScheduleValue> fetchScheduleValues = queueProcessor.queryTask(vmElasticJobConfig.getQueueId(), shardingTotalCount, shardingItem, fetchNmu);
                        if (fetchScheduleValues != null && !fetchScheduleValues.isEmpty()) {
                            scheduleValues.addAll(fetchScheduleValues);
                        }
//                        if(scheduleValues.isEmpty()){
//                            TimeUnit.SECONDS.sleep(1);
//                            scheduleValues.add(new BOVmScheduleBean());
//                        }
                        return scheduleValues;
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
        } else if (fetchModel == VMBootParam.QueueBootParam.FetchModel.ORDER_FIRST) {
            // TODO
            throw new RuntimeException("订单优先兜取模式暂不支持。");
        } else {
            throw new RuntimeException("不支持的兜取模式。");
        }
    }
}
