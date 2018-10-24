package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.config.AbstractApplicationContext;
import com.asiainfo.exeframe.elastic.config.ProcessDefinition;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.config.vm.VMDefinition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class VMApplicationContext extends AbstractApplicationContext {

    @Getter
    private List<VMDefinition> vmDefinitions;

    public VMApplicationContext(String location) {
        super(location);
    }

    @Override
    public void initProcessDefinition(List<ProcessDefinition> processDefinitions) {
        if (vmDefinitions == null) {
            vmDefinitions = new ArrayList<VMDefinition>();
        }
        for (ProcessDefinition processDefinition : processDefinitions) {
            if (processDefinition.getProcessType() == ProcessType.VM) {
                vmDefinitions.add((VMDefinition) processDefinition);
            }
        }


    }

}
