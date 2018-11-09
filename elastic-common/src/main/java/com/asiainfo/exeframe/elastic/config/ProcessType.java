package com.asiainfo.exeframe.elastic.config;

public enum ProcessType {
    VM, TF, BP;

    public static ProcessType toProcessType(String processType) {
        if (VM.name().equalsIgnoreCase(processType)) {
            return VM;
        } else if (TF.name().equalsIgnoreCase(processType)) {
            return TF;
        } else if (BP.name().equalsIgnoreCase(processType)) {
            return BP;
        } else {
            throw new RuntimeException("不支持的进程类型.");
        }
    }
}
