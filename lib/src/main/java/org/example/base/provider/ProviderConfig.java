package org.example.base.provider;

import java.util.Collections;
import java.util.Map;

public class ProviderConfig {
    private final Map<String, Object> config;

    public ProviderConfig(Map<String, Object> config) {
        this.config = Collections.unmodifiableMap(config);
    }

    public Map<String, Object> getConfig() {
        return config;
    }
}
