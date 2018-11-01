package com.asiainfo.exeframe.elastic;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AllotJobDataTask<T> implements Callable<Integer> {

    private ConcurrentLinkedQueue<T> dataQueue;

    private DataConsumer<T> dataConsumer;

    private AtomicBoolean needToEnd = new AtomicBoolean(false);

    private int allotDataSize;

    private int processSuccessSize;

    private int processFailureSize;

    private ListeningExecutorService consumerExecutorService;

    public <T> AllotJobDataTask(ConcurrentLinkedQueue dataQueue) {
        this.dataQueue = dataQueue;
        this.consumerExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1));
    }

    @Override
    public Integer call() throws Exception {
        while (!needToEnd.get()) {
            final T data = dataQueue.poll();
            while (data != null) {
                ListenableFuture<Void> future = consumerExecutorService.submit(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                });
                Futures.addCallback(future, new FutureCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                allotDataSize++;
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
        this.consumerExecutorService.shutdown();
        return allotDataSize;
    }
}
