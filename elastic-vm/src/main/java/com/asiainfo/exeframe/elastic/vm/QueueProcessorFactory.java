package com.asiainfo.exeframe.elastic.vm;

import com.ai.comframe.queue.*;
import com.ai.comframe.utils.WrapPropertiesUtil;
import org.apache.commons.lang3.StringUtils;

public class QueueProcessorFactory {

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
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
