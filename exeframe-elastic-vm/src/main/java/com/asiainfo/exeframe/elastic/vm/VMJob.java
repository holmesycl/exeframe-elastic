package com.asiainfo.exeframe.elastic.vm;

import com.ai.appframe2.complex.center.CenterFactory;
import com.ai.comframe.queue.*;
import com.ai.comframe.utils.DataSourceUtil;
import com.ai.comframe.utils.WrapPropertiesUtil;
import com.ai.comframe.vm.workflow.ivalues.IBOVmScheduleValue;
import com.ai.common.util.CenterUtil;
import com.asiainfo.exeframe.elastic.AbstractElasticJob;
import com.asiainfo.exeframe.elastic.config.vm.VMParam;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class VMJob extends AbstractElasticJob<IBOVmScheduleValue, Boolean> {

    private VMParam vmParam;

    private IQueueProcessor processor;

    private boolean isPushDataSource = false;

    private ListeningExecutorService executorService;

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
        try {
            return processor.queryTask(vmParam.getQueueId(), totalCount, item, vmParam.getFetchNum());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeProcessData(ShardingContext shardingContext, IBOVmScheduleValue item) throws Throwable {
        super.beforeProcessData(shardingContext, item);
        CenterFactory.setCenterInfoByTypeAndValue(CenterUtil.REGION_ID, item.getRegionId());
    }

    @Override
    public Boolean processData(ShardingContext shardingContext, IBOVmScheduleValue item) throws Throwable {
        return processor.execute(item);
    }

    @Override
    public void returnProcessData(ShardingContext shardingContext, IBOVmScheduleValue item) {
        super.returnProcessData(shardingContext, item);
        CenterFactory.setCenterInfoEmpty();
    }

    @Override
    public void beforeStreamingProcess(ShardingContext shardingContext) {
        super.beforeStreamingProcess(shardingContext);
        this.vmParam = GsonFactory.getGson().fromJson(shardingContext.getJobParameter(), VMParam.class);
        this.processor = getQueueProcessor(vmParam.getQueueType());
        try {
            isPushDataSource = DataSourceUtil.pushDataSourcebyQueueId(this.vmParam.getQueueId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterStreamingProcess(ShardingContext shardingContext, int processDataSize) {
        super.afterStreamingProcess(shardingContext, processDataSize);
        if (isPushDataSource) {
            try {
                DataSourceUtil.popDataSource();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static IQueueProcessor getQueueProcessor(String queueType) {
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
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
