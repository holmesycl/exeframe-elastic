package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.config.AbstractApplicationContext;
import com.asiainfo.exeframe.elastic.config.ProcessDefinition;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.config.vm.VMDefinition;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VMApplicationContext extends AbstractApplicationContext {

    @Getter
    private List<VMDefinition> vmDefinitions;

    public VMApplicationContext(String configFile) {
        super(configFile);
        vmDefinitions = new ArrayList<VMDefinition>();
    }

    public VMApplicationContext(String configFile, String... bootProcessCodes) {
        this(configFile);
        Set<String> bootProcessCodeSets = Sets.newHashSet(bootProcessCodes);
        for (ProcessDefinition processDefinition : getProcessDefinitions()) {
            if (processDefinition.getProcessType() == ProcessType.VM) {
                VMDefinition vmDefinition = (VMDefinition) processDefinition;
                if (bootProcessCodeSets.isEmpty() || bootProcessCodeSets.contains(vmDefinition.getVmParam().getQueueId())) {
                    vmDefinitions.add((VMDefinition) processDefinition);
                }
            }
        }
    }

}
