package com.asiainfo.exeframe.elastic;

public interface DataConsumer<T> {
    void process(T data);
}
