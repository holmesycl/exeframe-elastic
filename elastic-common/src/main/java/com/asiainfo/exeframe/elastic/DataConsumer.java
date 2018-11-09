package com.asiainfo.exeframe.elastic;

public interface DataConsumer<T, V> {
    V process(T data);
}
