package com.asiainfo.exeframe.elastic.config.bp;

import com.asiainfo.exeframe.elastic.config.ProcessDefinition;
import com.asiainfo.exeframe.elastic.config.ProcessType;

public class BPDefinition implements ProcessDefinition {
    @Override
    public ProcessType getProcessType() {
        return ProcessType.BP;
    }
}
