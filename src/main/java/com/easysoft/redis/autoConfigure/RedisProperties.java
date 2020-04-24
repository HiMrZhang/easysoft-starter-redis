package com.easysoft.redis.autoConfigure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;


/**
 * TODO
 *
 * @author： zyp[2305658511@qq.com]
 * @date： 2019-12-18 20:26
 * @version： V1.0
 * @review: zyp[2305658511@qq.com]/2019-12-18 20:26
 */
@Data
@ConfigurationProperties(prefix = "easysoft.redis")
public class RedisProperties {

    @Autowired
    private Environment env;

    private String namespace;

    private boolean namespaceEnable = true;

    /**
     * Redis 慢操作，单位：毫秒, 如果大于0时会打印慢日志
     */
    private int slowLogSlowerThan = 10;

    /**
     *
     */
    @PostConstruct
    public void init() {
        if (namespaceEnable && null != env) {
            if (null == namespace || namespace.trim().length() == 0) {
                String applicationName = env.getProperty("spring.application.name");
                if (null != applicationName && applicationName.trim().length() > 0) {
                    namespace = applicationName;
                }
            }
        }
    }
}
