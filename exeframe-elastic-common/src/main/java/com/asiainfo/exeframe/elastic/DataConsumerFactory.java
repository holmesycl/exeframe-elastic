package com.asiainfo.exeframe.elastic;

public interface DataConsumerFactory<T>{

    DataConsumer<T> create();

}
