package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.AbstractElasticJob;
import com.asiainfo.exeframe.elastic.ElasticFrameWork;
import com.asiainfo.exeframe.elastic.config.BootContext;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.vm.config.VMBootParam;

public class ElasticVMFrameWork extends ElasticFrameWork {

    public static void main(String[] args) {
        new ElasticVMFrameWork(args).start();
    }

    public ElasticVMFrameWork(String[] args) {
        super(args);
    }

    @Override
    protected BootContext createBootContext() {
        return new VMBootContext(new VMBootParam(getArgs()));
    }

    @Override
    protected Class<? extends AbstractElasticJob> elasticJobClass() {
        return VMElasticJob.class;
    }

    @Override
    protected ProcessType processType() {
        return ProcessType.VM;
    }
}
