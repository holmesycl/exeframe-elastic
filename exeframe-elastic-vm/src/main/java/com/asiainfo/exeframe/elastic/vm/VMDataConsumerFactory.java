package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.AbstractDataConsumerFactory;
import com.asiainfo.exeframe.elastic.DataConsumer;

public class VMDataConsumerFactory extends AbstractDataConsumerFactory {

    private String queueType;

    public VMDataConsumerFactory(String queueType) {
        this.queueType = queueType;
    }

    @Override
    public DataConsumer create() {
        return new VMDataConsumer(queueType);
    }
}
