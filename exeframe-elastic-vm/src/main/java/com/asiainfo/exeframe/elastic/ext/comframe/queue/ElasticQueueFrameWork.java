package com.asiainfo.exeframe.elastic.ext.comframe.queue;

import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.config.ivalues.IBOVmQueueConfigValue;
import com.ai.comframe.config.service.interfaces.IVmQueueConfigSV;
import com.ai.comframe.locale.ComframeLocaleFactory;
import com.ai.comframe.queue.QueueParam;
import com.ai.comframe.utils.PropertiesUtil;
import com.asiainfo.exeframe.elastic.db.JobEventDbConfiguration;
import com.asiainfo.exeframe.elastic.ext.comframe.job.VMScheduleJob;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.dataflow.DataflowJobConfiguration;
import io.elasticjob.lite.event.JobEventConfiguration;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import io.elasticjob.lite.reg.zookeeper.ZookeeperConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.lang.StringUtils;

public class ElasticQueueFrameWork {

    public ElasticQueueFrameWork() {
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_inputStartAttr"));
            System.out.println(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_queueID"));
            System.out.println(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_queueType"));
            System.out.println("Example：com.asiainfo.elastic.ext.comframe.queue.ElasticQueueFrameWork -q test -t workflow");
            System.exit(1);
        }
        if (args.length % 2 != 0) {
            System.out.println(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_startError"));
            System.exit(1);
        }
        try {
            QueueParam param = init(args);
            start(param);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void start(QueueParam param) {
        System.out.println(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_startAttr") + param.toString());        new JobScheduler(createRegistryCenter(), createJobConfiguration(), createJobEventConfiguration()).init();
    }

    private static JobEventConfiguration createJobEventConfiguration() {
        return new JobEventDbConfiguration();
    }

    private static LiteJobConfiguration createJobConfiguration() {
        String queueId = "ORD_R";
        String queueType = "workflow";
        int shardingTotalCount = 5;
        String jobNmae = queueId + "#" + queueType + "#" + VMScheduleJob.class.getSimpleName();
        IVmQueueConfigSV configsv = (IVmQueueConfigSV) ServiceFactory.getService(IVmQueueConfigSV.class);
        String second = "";
        try {
            IBOVmQueueConfigValue queueConfig = configsv.getVmQueueConfig(queueId, queueType);
            second = String.valueOf(queueConfig.getTimeInterval() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cron = "0/" + second + " * * * * ?";
        // 创建作业配置
        // ...
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobNmae, cron, shardingTotalCount).build();
        // 定义SIMPLE类型配置
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(simpleCoreConfig, VMScheduleJob.class.getCanonicalName(), true);
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
        return simpleJobRootConfig;
    }


    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("10.9.236.165:2181,10.9.236.166:2181,10.9.236.167:2181", "elastic-job"));
        regCenter.init();
        return regCenter;
    }


    private static QueueParam init(String[] args) throws Exception {
        QueueParam queueParam = paramParser(args);
        if (PropertiesUtil.isDev()) {
            String devName = PropertiesUtil.getDevId();
            String[] pParam = new String[]{"comframe.dev.name"};
            if (StringUtils.isBlank(devName)) {
                throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_devName", pParam));
            }
            queueParam.setDevName(devName);
        }
        if (StringUtils.isBlank(queueParam.getQueueId())) {
            throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_inputQueueID"));
        } else if (StringUtils.isBlank(queueParam.getQueueType())) {
            throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_inputQueueType"));
        } else {
            IVmQueueConfigSV configSv = (IVmQueueConfigSV) ServiceFactory.getService(IVmQueueConfigSV.class);
            IBOVmQueueConfigValue configValue = configSv.getVmQueueConfig(queueParam.getQueueId(), queueParam.getQueueType());
            String[] Param;
            if (null == configValue) {
                String[] param = new String[]{queueParam.getQueueId(), queueParam.getQueueType()};
                throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_notFindConfig", param));
            }
        }
        return queueParam;
    }

    private static QueueParam paramParser(String[] args) throws Exception {
        QueueParam queueParam = new QueueParam();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].startsWith("-")) {
                if ("-q".equalsIgnoreCase(args[i])) {
                    if (i + 1 > args.length - 1) {
                        throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_queueIDError"));
                    }
                    queueParam.setQueueId(args[i + 1]);
                }
                if ("-t".equalsIgnoreCase(args[i])) {
                    if (i + 1 > args.length - 1) {
                        throw new Exception(ComframeLocaleFactory.getResource("com.ai.comframe.queue.QueueFrameWork_queueTypeError"));
                    }
                    queueParam.setQueueType(args[i + 1]);
                }
            }
        }
        return queueParam;
    }
}
