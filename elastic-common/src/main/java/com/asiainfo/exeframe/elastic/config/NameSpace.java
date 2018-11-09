package com.asiainfo.exeframe.elastic.config;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class NameSpace {

    private static final String PREFIX_NODE = "elastic";

    private static final String SLASH = "/";

    @NonNull
    private String tenant;

    @NonNull
    private ProcessType processType;

    public String path() {
        return Joiner.on(SLASH).join(PREFIX_NODE, tenant, processType.name().toLowerCase());
    }

}
