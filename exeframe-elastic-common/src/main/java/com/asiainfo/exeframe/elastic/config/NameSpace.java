package com.asiainfo.exeframe.elastic.config;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NameSpace {

    private static final String PREFIX = "elastic";
    private static final String HYPHEN = "-";

    @NonNull
    private String whoami;

    @NonNull
    private ProcessType processType;

    public String name() {
        return Joiner.on(HYPHEN).join(PREFIX, whoami, processType.name().toLowerCase());
    }

}
