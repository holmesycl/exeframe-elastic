package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.IQueueProcessor;
import com.asiainfo.exeframe.elastic.DataFetcher;

import java.util.List;

public class VMDataFetcher<IBOVmScheduleValue> implements DataFetcher<IBOVmScheduleValue> {


    private IQueueProcessor processor;

    private String queueId;

    private String queueType;

    public VMDataFetcher(String queueId, String queueType) {
        this.queueId = queueId;
        this.queueType = queueType;
        this.processor = QueueProcessorFactory.getQueueProcessor(this.queueType);
    }

    @Override
    public List<IBOVmScheduleValue> fetchData(int shardingItem, int shardingTotalCount, int fetchNum) {
        try {
            return processor.queryTask(queueId, shardingTotalCount, shardingItem, fetchNum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
