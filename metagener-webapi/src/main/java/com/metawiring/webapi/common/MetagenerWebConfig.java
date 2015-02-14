package com.metawiring.webapi.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public class MetagenerWebConfig extends Configuration {

    @NotEmpty
    private String defaultName = "metagener-webapi";

    @NotEmpty
    private Map<String,String> preload;

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public Map<String,String> getPreload() {
        return preload;
    }

    @JsonProperty
    public void setPreload(Map<String,String> preload) {
        this.preload = preload;
    }


}
