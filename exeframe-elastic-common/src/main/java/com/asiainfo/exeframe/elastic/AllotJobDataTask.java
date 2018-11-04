package com.asiainfo.exeframe.elastic;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AllotJobDataTask<T> implements Callable<Integer> {

    private ConcurrentLinkedQueue<T> dataQueue;

    private DataConsumer<T> dataConsumer;

    private AtomicBoolean needToEnd;

    private AtomicInteger allotDataSize = new AtomicInteger(0);

    private AtomicInteger processSuccessSize = new AtomicInteger(0);

    private AtomicInteger processFailureSize = new AtomicInteger(0);

    private ListeningExecutorService consumerExecutorService;

    public <T> AllotJobDataTask(ConcurrentLinkedQueue dataQueue, DataConsumer dataConsumer, AtomicBoolean needToEnd) {
        this.dataQueue = dataQueue;
        this.dataConsumer = dataConsumer;
        this.needToEnd = needToEnd;
        this.consumerExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1));
    }

    @Override
    public Integer call() throws Exception {
        while (!needToEnd.get()) {
            T data;
            while ((data = dataQueue.poll()) != null) {
                final T processData = data;
                ListenableFuture<Void> future = consumerExecutorService.submit(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        dataConsumer.process(processData);
                        return null;
                    }
                });

                allotDataSize.incrementAndGet();

                Futures.addCallback(future, new FutureCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        processSuccessSize.incrementAndGet();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        processSuccessSize.incrementAndGet();
                    }
                });
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
        this.consumerExecutorService.shutdown();
        // 等待所有数据处理完毕
        while (allotDataSize.get() != (processSuccessSize.get() + processFailureSize.get())) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        return allotDataSize.get();
    }
}
