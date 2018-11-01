package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.BootParam;
import io.elasticjob.lite.api.ShardingContext;

public abstract class AbstractDataConsumerFactory<T>{

    public abstract DataConsumer<T> create();

}
