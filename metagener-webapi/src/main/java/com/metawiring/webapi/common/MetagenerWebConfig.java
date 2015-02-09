package com.metawiring.webapi.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class MetagenerWebConfig extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "MetagenerWeb";

    @NotEmpty
    private String metagenerConfig;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public String getMetagenerConfig() {
        return metagenerConfig;
    }

    @JsonProperty
    public void setMetagenerConfig(String metagenerConfig) {
        this.metagenerConfig = metagenerConfig;
    }
}
