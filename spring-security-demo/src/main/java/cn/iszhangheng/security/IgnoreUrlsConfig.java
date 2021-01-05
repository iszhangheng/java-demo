package cn.iszhangheng.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(IgnoreUrlsConfig.class)
public class IgnoreUrlsConfig {
    public String[] getUrls() {
        return new String[0];
    }
}
