package com.asiainfo.exeframe.elastic.config.vm;

import com.asiainfo.exeframe.elastic.config.ProcessDefinition;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import lombok.Data;

@Data
public class VMDefinition implements ProcessDefinition {

    private VMParam vmParam;

    @Override
    public ProcessType getProcessType() {
        return ProcessType.VM;
    }
}
