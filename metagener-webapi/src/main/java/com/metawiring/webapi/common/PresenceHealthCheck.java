package com.metawiring.webapi.common;

import com.codahale.metrics.health.HealthCheck;

public class PresenceHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
