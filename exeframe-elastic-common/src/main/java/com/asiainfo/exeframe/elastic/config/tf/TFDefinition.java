package com.asiainfo.exeframe.elastic.config.tf;

import com.asiainfo.exeframe.elastic.config.ProcessDefinition;
import com.asiainfo.exeframe.elastic.config.ProcessType;

public class TFDefinition implements ProcessDefinition {
    @Override
    public ProcessType getProcessType() {
        return ProcessType.TF;
    }
}
