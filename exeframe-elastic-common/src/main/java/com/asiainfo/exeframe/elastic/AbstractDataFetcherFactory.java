package com.asiainfo.exeframe.elastic;

import io.elasticjob.lite.api.ShardingContext;

public abstract class AbstractDataFetcherFactory<T>{

    public abstract DataFetcher<T> create();

}
