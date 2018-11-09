package com.asiainfo.exeframe.elastic.vm.config;

import com.asiainfo.exeframe.elastic.config.BootParam;
import com.google.common.base.Preconditions;
import com.google.gson.reflect.TypeToken;
import io.elasticjob.lite.util.json.GsonFactory;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Log4j
public class VMBootParam extends BootParam {

    @Getter
    private Set<QueueBootParam> queueBootParams = new HashSet<QueueBootParam>();

    @Data
    public static class QueueBootParam {
        private String queueId;
        private String queueType;
        private FetchModel fetchModel = FetchModel.RANDOM;

        /**
         * 兜取模式：订单优先，随机（默认）
         */
        public static enum FetchModel {
            RANDOM, ORDER_FIRST;

            public static FetchModel from(String model) {
                if (StringUtils.equalsIgnoreCase(model, RANDOM.name())) {
                    return RANDOM;
                } else if (StringUtils.equalsIgnoreCase(model, ORDER_FIRST.name())) {
                    return ORDER_FIRST;
                }
                throw new RuntimeException("不支持的兜取模式。");
            }
        }
    }

    public VMBootParam(String[] args) {
        super(args);
        // 启动多个工作流：[{"queueId":"ORD_R","queueType":"workflow","fetchModel":"ORDER_FIRST"},{"queueId":"ORD_B","queueType":"workflow","fetchModel":"ORDER_FIRST"}]
        // 启动单个工作流：{"queueId":"ORD_R","queueType":"workflow","fetchModel":"ORDER_FIRST"}
        Preconditions.checkArgument(args != null && args.length == 1, "入参不能为空。" +
                "参考格式：" +
                "启动多个工作流 - [{\"queueId\":\"ORD_R\",\"queueType\":\"workflow\",\"fetchModel\":\"ORDER_FIRST\"},{\"queueId\":\"ORD_B\",\"queueType\":\"workflow\",\"fetchModel\":\"ORDER_FIRST\"}]" +
                "启动单个工作流 - {\"queueId\":\"ORD_R\",\"queueType\":\"workflow\",\"fetchModel\":\"ORDER_FIRST\"}");
        String bootParamJson = args[0];
        log.error("工作流启动参数：" + bootParamJson);
        try {
            queueBootParams.add(GsonFactory.getGson().fromJson(bootParamJson, QueueBootParam.class));
        } catch (Exception e) {
            Set<QueueBootParam> bootParams = GsonFactory.getGson().fromJson(args[0], new TypeToken<Set<QueueBootParam>>() {
            }.getType());
            for (QueueBootParam bootParam :
                    bootParams) {
                queueBootParams.add(bootParam);
            }
        }
        log.error("根据启动参数确定需要启动【" + queueBootParams.size() + "】个工作流任务。详情如下：");
        Iterator<QueueBootParam> iterator = queueBootParams.iterator();
        while (iterator.hasNext()) {
            log.error("工作流任务：" + iterator.next());
        }
    }
}
