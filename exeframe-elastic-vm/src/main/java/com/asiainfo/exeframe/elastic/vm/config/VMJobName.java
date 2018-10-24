package com.asiainfo.exeframe.elastic.vm.config;

import com.asiainfo.exeframe.elastic.config.JobName;
import com.asiainfo.exeframe.elastic.config.vm.VMParam;
import com.google.common.base.Joiner;

public class VMJobName extends JobName {

    public VMJobName(VMParam vmParam) {
        setName(Joiner.on("~").join(vmParam.getQueueId(), vmParam.getQueueType()));
    }

}
