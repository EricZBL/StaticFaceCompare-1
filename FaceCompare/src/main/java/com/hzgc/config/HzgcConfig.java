package com.hzgc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hzgc.person")
public class HzgcConfig {

    private String dir;

    private float sim;

    public float getSim() {
        return sim;
    }

    public void setSim(float sim) {
        this.sim = sim;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
