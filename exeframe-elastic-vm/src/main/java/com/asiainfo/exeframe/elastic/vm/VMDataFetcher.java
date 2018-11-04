package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.IQueueProcessor;
import com.ai.comframe.utils.DataSourceUtil;
import com.asiainfo.exeframe.elastic.DataFetcher;

import java.util.List;

public class VMDataFetcher<IBOVmScheduleValue> extends DataFetcher<IBOVmScheduleValue> {


    private IQueueProcessor processor;

    private String queueId;

    private String queueType;

    public VMDataFetcher(String queueId, String queueType) {
        this.queueId = queueId;
        this.queueType = queueType;
        this.processor = QueueProcessorFactory.getQueueProcessor(this.queueType);
    }

    @Override
    protected List<IBOVmScheduleValue> fetchData(int shardingTotalCount, int shardingItem, int fetchNmu) {
        try {
            DataSourceUtil.pushDataSourcebyQueueId(queueId);
            return processor.queryTask(queueId, shardingTotalCount, shardingItem, fetchNmu);
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
}
