package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.JobConfig;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.util.json.GsonFactory;

import java.util.List;

public interface DataFetcher<T> {

    List<T> fetchData(int shardingTotalCount, int shardingItem, int fetchNmu);

}
