package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.DataFetcherFactory;
import com.asiainfo.exeframe.elastic.DataFetcher;

public class VMDataFetcherFactory implements DataFetcherFactory {

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
