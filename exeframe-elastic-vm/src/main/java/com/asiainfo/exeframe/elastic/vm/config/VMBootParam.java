package com.asiainfo.exeframe.elastic.vm.config;

import com.asiainfo.exeframe.elastic.config.BootParam;
import com.asiainfo.exeframe.elastic.util.Sign;
import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class VMBootParam extends BootParam {

    @Getter
    private Set<QueueBootParam> queueBootParams = new HashSet<QueueBootParam>();

    @Data
    public class QueueBootParam {
        private String queueId;
        private String queueType;
    }

    public VMBootParam(String[] args) {
        super(args);
        // 格式：ord_r workflow,exception ord_b workflow,exception
        Preconditions.checkArgument(args != null && args.length > 0, "入参不能为空。参考格式：ord_r workflow,exception ord_b workflow,exception");
        Preconditions.checkArgument(args.length % 2 == 0, "入参格式不正确。参考格式：ord_r workflow,exception ord_b workflow,exception");
        for (int i = 0; i < args.length; ) {
            String queueId = args[i++];
            String types = args[i++];
            String[] queueTypes = types.split(Sign.COMMA.mark());
            for (String queueType :
                    queueTypes) {
                QueueBootParam queueBootParam = new QueueBootParam();
                queueBootParam.setQueueId(queueId);
                queueBootParam.setQueueType(queueType);
                this.queueBootParams.add(queueBootParam);
            }
        }
    }

}
