package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.JobConfig;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.util.json.GsonFactory;

import java.util.List;

public abstract class DataFetcher<T> {

    List<T> fetchData(ShardingContext shardingContext) {
        JobConfig jobConfig = GsonFactory.getGson().fromJson(shardingContext.getJobParameter(), JobConfig.class);
        return fetchData(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem(), jobConfig.getFetchNmu());
    }

    protected abstract List<T> fetchData(int shardingTotalCount, int shardingItem, int fetchNmu);

}
