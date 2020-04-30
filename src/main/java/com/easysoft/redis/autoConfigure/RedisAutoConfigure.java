package com.easysoft.redis.autoConfigure;

import com.easysoft.redis.IRedisOperater;
import com.easysoft.redis.RedisOperater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * TODO
 *
 * @author： zyp[2305658511@qq.com]
 * @date： 2019-11-06 10:46
 * @version： V1.0
 * @review: zyp[2305658511@qq.com]/2019-11-06 10:46
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfigure {
    @Bean
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory,RedisSerializer valueSerializer) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        /**
         * value值的序列化采用GenericJackson2JsonRedisSerializer
         */
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        /**
         * key的序列化采用StringRedisSerializer
         */
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(IRedisOperater.class)
    public IRedisOperater redisOperater(RedisProperties redisProperties, RedisTemplate redisTemplate) {
        RedisOperater redisOperater = new RedisOperater(redisProperties, redisTemplate);
        return redisOperater;
    }
}
