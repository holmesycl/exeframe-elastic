package com.asiainfo.exeframe.elastic;

import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j
public class Statistics {
    private static Statistics ourInstance = new Statistics();

    public static Statistics getInstance() {
        return ourInstance;
    }

    private ConcurrentHashMap<String, AtomicInteger> jobItemCur = new ConcurrentHashMap<String, AtomicInteger>();
    private ConcurrentHashMap<String, AtomicInteger> jobItemHis = new ConcurrentHashMap<String, AtomicInteger>();

    private Statistics() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    for (Map.Entry<String, AtomicInteger> entry : jobItemCur.entrySet()) {
                        String key = entry.getKey();
                        int cur = entry.getValue().intValue();
                        AtomicInteger hisCount = jobItemHis.get(key);
                        if (hisCount == null) {
                            hisCount = new AtomicInteger(0);
                        }
                        int his = hisCount.intValue();
                        int diff = cur - his;
                        hisCount.addAndGet(diff);
                        jobItemHis.put(key, hisCount);
                        log.error("任务项：" + key + "处理效率：" + ((cur - his) / 5) + "/秒");
                    }
                    TimeUnit.SECONDS.sleep(5);
                }
            }
        });
    }

    public int incrementAndGet(String jobItem) {
        AtomicInteger count = jobItemCur.get(jobItem);
        if (count == null) {
            count = new AtomicInteger(0);
            jobItemCur.put(jobItem, count);
        }
        return count.incrementAndGet();
    }


}
