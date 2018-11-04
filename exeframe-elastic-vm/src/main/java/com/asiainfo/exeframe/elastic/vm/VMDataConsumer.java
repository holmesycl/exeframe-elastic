package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.IQueueProcessor;
import com.ai.comframe.utils.DataSourceUtil;
import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.asiainfo.exeframe.elastic.DataConsumer;

public class VMDataConsumer implements DataConsumer<IBOVmScheduleValue> {

    private IQueueProcessor processor;

    public VMDataConsumer(String queueType) {
        this.processor = QueueProcessorFactory.getQueueProcessor(queueType);
    }

    @Override
    public void process(IBOVmScheduleValue data) {
        try {
            DataSourceUtil.pushDataSourcebyQueueId(data.getQueueId());
            processor.execute(data);
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
