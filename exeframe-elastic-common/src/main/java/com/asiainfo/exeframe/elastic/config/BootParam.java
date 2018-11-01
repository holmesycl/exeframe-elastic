package com.asiainfo.exeframe.elastic.config;

import io.elasticjob.lite.util.json.GsonFactory;
import lombok.Data;

@Data
public abstract class BootParam {

    private String[] args;

    public BootParam(String[] args) {
        this.args = args;
    }

    public static BootParam fromJson(String json, Class<? extends BootParam> classOfT) {
        return GsonFactory.getGson().fromJson(json, classOfT);
    }

    public String toJson() {
        return GsonFactory.getGson().toJson(this);
    }

}
