package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.AbstractDataFetcherFactory;
import com.asiainfo.exeframe.elastic.DataFetcher;

public class VMDataFetcherFactory extends AbstractDataFetcherFactory {

    private String queueId;
    private String queueType;

    public VMDataFetcherFactory(String queueId, String queueType) {
        this.queueId = queueId;
        this.queueType = queueType;
    }

    @Override
    public DataFetcher create() {
        return new VMDataFetcher(queueId, queueType);
    }

}
