package com.asiainfo.exeframe.elastic;

public interface DataFetcherFactory<T>{

    DataFetcher<T> create();

}
