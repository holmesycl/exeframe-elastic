package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.vm.ElasticVMFrameWork;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ElasticVMFrameWorkTest {

    @Test
    public void testMain(){
        ElasticVMFrameWork.main(null);
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
