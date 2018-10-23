package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.ext.comframe.queue.ElasticQueueFrameWork;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ElasticQueueFrameWorkTest {

    @Test
    public void testMain(){
        String[] args = new String[]{"-q", "ORD_R", "-t", "workflow"};
        ElasticQueueFrameWork.main(args);
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
