package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.vm.ElasticVMFrameWork;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ElasticVMFrameWorkTest {

    @Test
    public void testMain() {
        String[] args = new String[]{"[{\"queueId\":\"ORD_R\",\"queueType\":\"workflow\",\"fetchModel\":\"ORDER_FIRST\"},{\"queueId\":\"ORD_B\",\"queueType\":\"workflow\"}]"};
        // String[] args = new String[]{"{\"queueId\":\"ORD_R\",\"queueType\":\"workflow\",\"fetchModel\":\"ORDER_FIRST\"}"};
        ElasticVMFrameWork.main(args);
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
