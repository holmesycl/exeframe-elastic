package com.asiainfo.exeframe.elastic.vm;

import com.asiainfo.exeframe.elastic.config.NameSpace;
import com.asiainfo.exeframe.elastic.config.vm.VMDefinition;
import com.asiainfo.exeframe.elastic.config.vm.VMParam;
import com.asiainfo.exeframe.elastic.vm.config.VMJobName;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.dataflow.DataflowJobConfiguration;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import io.elasticjob.lite.reg.zookeeper.ZookeeperConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import io.elasticjob.lite.util.json.GsonFactory;

import java.util.List;

public class ElasticVMFrameWork {

    public static void main(String[] args) {
        VMApplicationContext applicationContext = new VMApplicationContext("elastic-exeframe.yaml");
        List<VMDefinition> vmDefinitions = applicationContext.getVmDefinitions();
        for (VMDefinition vmDefinition : vmDefinitions) {
            CoordinatorRegistryCenter registryCenter = createCoordinatorRegistryCenter(applicationContext, vmDefinition);
            LiteJobConfiguration liteJobConfiguration = createLiteJobConfiguration(applicationContext, vmDefinition);
            new JobScheduler(registryCenter, liteJobConfiguration).init();
        }
    }

    private static LiteJobConfiguration createLiteJobConfiguration(VMApplicationContext applicationContext, VMDefinition vmDefinition) {
        VMParam vmParam = vmDefinition.getVmParam();
        String cron = "0/" + vmParam.getIntervalSecond() + " * * * * ?";
        String jobName = new VMJobName(vmParam).getName();
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, vmParam.getShardingTotalCount()).jobParameter(GsonFactory.getGson().toJson(vmDefinition.getVmParam())).build();
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfiguration, VMJob.class.getCanonicalName(), true);
        return LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
    }

    private static CoordinatorRegistryCenter createCoordinatorRegistryCenter(VMApplicationContext applicationContext, VMDefinition vmDefinition) {
        NameSpace nameSpace = new NameSpace(applicationContext.getWhoami(), vmDefinition.getProcessType());
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(applicationContext.getDefaultZkconnectString(), nameSpace.name()));
        regCenter.init();
        return regCenter;
    }
}
