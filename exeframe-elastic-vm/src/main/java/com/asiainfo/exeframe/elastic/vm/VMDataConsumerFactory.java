package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.DataConsumer;
import com.asiainfo.exeframe.elastic.DataConsumerFactory;

public class VMDataConsumerFactory implements DataConsumerFactory {

    private String queueType;

    public VMDataConsumerFactory(String queueType) {
        this.queueType = queueType;
    }

    @Override
    public DataConsumer create() {
        return new VMDataConsumer(queueType);
    }
}
