package com.asiainfo.exeframe.elastic;

import java.util.List;

public interface DataFetcher<T> {

    List<T> fetchData(int shardingItem, int shardingTotalCount, int fetchNum);

}
