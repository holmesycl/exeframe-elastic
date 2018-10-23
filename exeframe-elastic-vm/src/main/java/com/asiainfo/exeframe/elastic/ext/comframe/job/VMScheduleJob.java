package com.asiainfo.exeframe.elastic.ext.comframe.job;

import com.ai.appframe2.complex.center.CenterFactory;
import com.ai.appframe2.service.ServiceFactory;
import com.ai.comframe.config.ivalues.IBOVmQueueConfigValue;
import com.ai.comframe.config.service.interfaces.IVmQueueConfigSV;
import com.ai.comframe.queue.*;
import com.ai.comframe.utils.DataSourceUtil;
import com.ai.comframe.utils.WrapPropertiesUtil;
import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.ai.common.util.CenterUtil;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class VMScheduleJob implements DataflowJob<IBOVmScheduleValue> {

    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List<IBOVmScheduleValue> fetchData(ShardingContext shardingContext) {
        int totalCount = shardingContext.getShardingTotalCount();
        int item = shardingContext.getShardingItem();
        String jobName = shardingContext.getJobName();
        String[] args = jobName.split("#");
        String queueId = args[0];
        String queueType = args[1];
        IVmQueueConfigSV configsv = (IVmQueueConfigSV) ServiceFactory.getService(IVmQueueConfigSV.class);
        try {
            IBOVmQueueConfigValue queueConfig = configsv.getVmQueueConfig(queueId, queueType);
            int fetchNum = queueConfig.getFetchNum();

            IQueueProcessor processor = getQueueProcessor(queueType);
            boolean isPushDataSource = false;
            try {
                try {
                    isPushDataSource = DataSourceUtil.pushDataSourcebyQueueId(queueId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List queryList = null;
                try {
                    queryList = processor.queryTask(queueId, totalCount, item, fetchNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (queryList != null && queryList.size() > 0) {
                    for (int i = 0; i < queryList.size(); ++i) {
                        try {
                            IBOVmScheduleValue schedule = (IBOVmScheduleValue) queryList.get(i);
                            // 设置中心
                            CenterFactory.setCenterInfoByTypeAndValue(CenterUtil.REGION_ID, schedule.getRegionId());
                            processor.execute(schedule);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                if (isPushDataSource) {
                    try {
                        DataSourceUtil.popDataSource();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static IQueueProcessor getQueueProcessor(String queueType) {
        IQueueProcessor result = null;
        if ("workflow".equals(queueType)) {
            result = new WorkflowQueueProcessor();
        } else if ("timer".equals(queueType)) {
            result = new TimerQueueProcessor();
        } else if ("exception".equals(queueType)) {
            result = new ExceptionQueueProcessor();
        } else if ("warning".equals(queueType)) {
            result = new WarningQueueProcessor();
        } else if ("scanengine".equals(queueType) || "scanbusi".equals(queueType)) {
            result = getWrapQueueProcessor(queueType);
        }

        return (IQueueProcessor) result;
    }

    private static IQueueProcessor getWrapQueueProcessor(String queueType) {
        try {
            String className = WrapPropertiesUtil.getWrapQueueProcessor(queueType);
            if (StringUtils.isBlank(className)) {
                throw new Exception("找不到商用流程引擎的队列实现类！");
            } else {
                Object processorObj = Class.forName(className).newInstance();
                if (!(processorObj instanceof IQueueProcessor)) {
                    throw new Exception("配置商用流程引擎队列：" + queueType + ",处理类未实现IQueueProcessor接口！" + className);
                } else {
                    return (IQueueProcessor) processorObj;
                }
            }
        } catch (Throwable var4) {
            throw new RuntimeException(var4);
        }
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data            待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<IBOVmScheduleValue> data) {

    }
}
