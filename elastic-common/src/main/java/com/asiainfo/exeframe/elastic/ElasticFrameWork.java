package com.asiainfo.exeframe.elastic;

import com.asiainfo.exeframe.elastic.config.BootContext;
import com.asiainfo.exeframe.elastic.config.ElasticJobConfig;
import com.asiainfo.exeframe.elastic.config.NameSpace;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.util.Sign;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.dataflow.DataflowJobConfiguration;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import io.elasticjob.lite.reg.zookeeper.ZookeeperConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.Data;

@Data
public abstract class ElasticFrameWork {

    private String[] args;

    public ElasticFrameWork(String[] args) {
        this.args = args;
    }

    public void start() {
        BootContext bootContext = createBootContext();
        bootContext.init();
        for (ElasticJobConfig elasticJobConfig : bootContext.getElasticJobConfigs()) {
            startElasticJob(elasticJobConfig);
        }
    }

    private void startElasticJob(ElasticJobConfig elasticJobConfig) {
        CoordinatorRegistryCenter registryCenter = createCoordinatorRegistryCenter(elasticJobConfig);
        registryCenter.init();
        LiteJobConfiguration liteJobConfiguration = createLiteJobConfiguration(elasticJobConfig);
        new JobScheduler(registryCenter, liteJobConfiguration, new DataConsumerExecutorServiceListener()).init();
    }

    protected abstract BootContext createBootContext();

    protected abstract Class<? extends AbstractElasticJob> elasticJobClass();

    protected abstract ProcessType processType();

    private LiteJobConfiguration createLiteJobConfiguration(ElasticJobConfig elasticJobConfig) {
        String cron = "0" + Sign.SLASH.mark() + elasticJobConfig.getTimeInterval() / 1000 + " * * * * ?";
        JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(elasticJobConfig.getJobName(), cron, elasticJobConfig.getShardingTotalCount()).jobParameter(GsonFactory.getGson().toJson(elasticJobConfig)).build();
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfiguration, elasticJobClass().getCanonicalName(), true);
        return LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
    }

    private CoordinatorRegistryCenter createCoordinatorRegistryCenter(ElasticJobConfig elasticJobConfig) {
        NameSpace nameSpace = new NameSpace(elasticJobConfig.getTenant(), processType());
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration(elasticJobConfig.getZkConnectionString(), nameSpace.path()));
        return regCenter;
    }
}
