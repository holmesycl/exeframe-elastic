package com.asiainfo.exeframe.elastic.vm;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.config.ivalues.IBOVmQueueConfigValue;
import com.ai.comframe.config.service.interfaces.IVmQueueConfigSV;
import com.asiainfo.exeframe.elastic.config.BootContext;
import com.asiainfo.exeframe.elastic.config.ElasticJobConfig;
import com.asiainfo.exeframe.elastic.config.JobConfig;
import com.asiainfo.exeframe.elastic.config.ProcessType;
import com.asiainfo.exeframe.elastic.config.vm.VMElasticJobConfig;
import com.asiainfo.exeframe.elastic.util.Sign;
import com.asiainfo.exeframe.elastic.vm.config.VMBootParam;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Log4j
@Data
public class VMBootContext extends BootContext {

    private VMBootParam vmBootParam;

    public VMBootContext(VMBootParam vmBootParam) {
        super();
        this.vmBootParam = vmBootParam;
    }

    @Override
    protected List<ElasticJobConfig> loadConfig(Properties properties) {
        String prefix = Joiner.on(Sign.DOT.mark()).join(getTenant(), ProcessType.VM.name().toLowerCase());
        IVmQueueConfigSV configSV = (IVmQueueConfigSV) ServiceFactory.getService(IVmQueueConfigSV.class);
        Set<VMBootParam.QueueBootParam> queueBootParams = vmBootParam.getQueueBootParams();
        List<ElasticJobConfig> elasticJobConfigs = new ArrayList<ElasticJobConfig>(queueBootParams.size());
        for (VMBootParam.QueueBootParam queueBootParam :
                queueBootParams) {
            VMElasticJobConfig vmElasticJobConfig = new VMElasticJobConfig();
            vmElasticJobConfig.setTenant(getTenant());
            vmElasticJobConfig.setZkConnectionString(getZkConnectionString());

            String queueId = queueBootParam.getQueueId();
            vmElasticJobConfig.setQueueId(queueId);

            String queueType = queueBootParam.getQueueType();
            vmElasticJobConfig.setQueueType(queueType);

            final String PROPERTY_JOB_NAME = Joiner.on(Sign.DOT.mark()).join(prefix, queueId, queueType, JobConfig.JOB_NAME);
            String jobName = properties.getProperty(PROPERTY_JOB_NAME);
            Preconditions.checkState(StringUtils.isNotBlank(jobName), "根据属性%s无法从配置文件获取作业名。", PROPERTY_JOB_NAME);
            vmElasticJobConfig.setJobName(jobName);

            final String PROPERTY_SHARDING_TO_TOTAL_COUNT = Joiner.on(Sign.DOT.mark()).join(prefix, queueId, queueType, JobConfig.JOB_NAME);
            String shardingTotalCount = properties.getProperty(Joiner.on(Sign.DOT.mark()).join(prefix, queueId, queueType, ElasticJobConfig.SHARDING_TO_TOTAL_COUNT));
            Preconditions.checkState(StringUtils.isNotBlank(shardingTotalCount), "根据属性%s无法从配置文件获取分片数。", PROPERTY_SHARDING_TO_TOTAL_COUNT);
            Preconditions.checkState(NumberUtils.isDigits(shardingTotalCount), "属性%s必须是数字，当前为%s。", PROPERTY_SHARDING_TO_TOTAL_COUNT, shardingTotalCount);
            vmElasticJobConfig.setShardingTotalCount(Integer.parseInt(shardingTotalCount));

            try {
                IBOVmQueueConfigValue vmQueueConfigValue = configSV.getVmQueueConfig(queueId, queueType);
                int fetchNmu = vmQueueConfigValue.getFetchNum();
                vmElasticJobConfig.setFetchNmu(fetchNmu);
                long timeInterval = vmQueueConfigValue.getTimeInterval();
                vmElasticJobConfig.setTimeInterval(timeInterval);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            vmElasticJobConfig.setJobParameter(GsonFactory.getGson().toJson(vmElasticJobConfig));
            elasticJobConfigs.add(vmElasticJobConfig);
        }
        return elasticJobConfigs;
    }
}
