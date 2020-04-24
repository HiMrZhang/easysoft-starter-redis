package com.easysoft.redis;

import com.easysoft.redis.autoConfigure.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author： zyp[2305658511@qq.com]
 * @date： 2019-12-18 20:32
 * @version： V1.0
 * @review: zyp[2305658511@qq.com]/2019-12-18 20:32
 */
@Slf4j
public class RedisOperater implements IRedisOperater {

    protected final RedisProperties config;


    private final RedisTemplate redisTemplate;

    public RedisOperater(RedisProperties config, RedisTemplate redisTemplate) {
        this.config = config;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean exists(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.hasKey(k);
        });
    }

    @Override
    public Long delByPattern(String pattern) {
        return 0L;
    }

    @Override
    public <T> void set(String key, T value) {
        this.doCommand(key, (k) -> {
            redisTemplate.opsForValue().set(k, value);
            return null;
        });
    }


    @Override
    public <T> Boolean setex(String key, T value, int expire) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().setIfPresent(k, value, Duration.ofSeconds(expire));
        });
    }


    @Override
    public <T> Boolean setnx(String key, T value, int expire) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().setIfAbsent(k, value, Duration.ofSeconds(expire));
        });
    }


    @Override
    public <T> T get(String key) {
        return (T) this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().get(k);
        });
    }

    @Override
    public Long getLong(String key) {
        return this.doCommand(key, (k) -> {
            Object value = redisTemplate.opsForValue().get(k);
            if (value instanceof Integer) {
                return ((Integer) value).longValue();
            }
            return (Long) value;
        });
    }

    @Override
    public <T> T getSet(String key, T value) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForValue().getAndSet(k, value);
        });
    }

    @Override
    public Long getSetLong(String key, Long value) {
        return this.doCommand(key, (k) -> {
            Object preValue = redisTemplate.opsForValue().getAndSet(k, value);
            if (preValue instanceof Integer) {
                return ((Integer) preValue).longValue();
            }
            return (Long) preValue;
        });
    }

    @Override
    public Boolean delete(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.delete(k);
        });
    }

    @Override
    public Boolean expire(String key, int expire) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.expire(k, expire, TimeUnit.SECONDS);
        });
    }

    @Override
    public Long ttl(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.getExpire(k, TimeUnit.SECONDS);
        });
    }

    @Override
    public Long incr(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().increment(k);
        });
    }

    @Override
    public Long incrBy(String key, int increment) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().increment(k, increment);
        });
    }

    @Override
    public Long decr(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().decrement(k);
        });
    }

    @Override
    public Long decrBy(String key, int decrement) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForValue().decrement(k, decrement);
        });
    }

    @Override
    public <F> Boolean hexists(String key, F field) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().hasKey(k, field);
        });
    }

    @Override
    public <F, T> void hset(String key, F field, T value) {
        this.doCommand(key, (k) -> {
            redisTemplate.opsForHash().put(k, field, value);
            return null;
        });
    }

    @Override
    public <F, T> Boolean hsetnx(String key, F field, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().putIfAbsent(k, field, value);
        });
    }

    @Override
    public <F, T> void hmset(String key, Map<F, T> fieldValues) {
        this.doCommand(key, (k) -> {
            redisTemplate.opsForHash().putAll(k, fieldValues);
            return null;
        });
    }

    @Override
    public <F, T> T hget(String key, F field) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForHash().get(k, field);
        });
    }

    @Override
    public <T> Map<String, T> hgetAll(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().entries(k);
        });
    }

    @Override
    public <T> List<T> hmget(String key, Object... field) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().multiGet(k, CollectionUtils.arrayToList(field));
        });
    }

    @Override
    public Long hdel(String key, Object... field) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().delete(k, field);
        });
    }

    @Override
    public <F> Long hincrBy(String key, F field, int increment) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForHash().increment(k, field, increment);
        });
    }

    @Override
    public <T> T lpop(String key) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForList().leftPop(k);
        });
    }

    @Override
    public <T> T rpop(String key) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForList().rightPop(k);
        });
    }

    @Override
    public <T> Long lpush(String key, T... value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().leftPushAll(k, value);
        });
    }

    @Override
    public <T> Long lpushx(String key, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().leftPushIfPresent(k, value);
        });
    }

    @Override
    public <T> Long rpush(String key, T... value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().rightPushAll(k, value);
        });
    }

    @Override
    public <T> Long rpushx(String key, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().rightPushIfPresent(k, value);
        });
    }

    @Override
    public <T> T lindex(String key, int index) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForList().index(k, index);
        });
    }

    @Override
    public <T> Long linsertAfter(String key, T pivot, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().rightPush(k, pivot, value);
        });
    }

    @Override
    public <T> Long linsertBefore(String key, T pivot, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().leftPush(k, pivot, value);
        });
    }

    @Override
    public Long llen(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().size(k);
        });
    }

    @Override
    public <T> List<T> lrange(String key, int start, int stop) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().range(k, start, stop);
        });
    }

    @Override
    public <T> Long lrem(String key, int count, T value) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForList().remove(k, count, value);
        });
    }

    @Override
    public <T> void lset(String key, int index, T value) {
        this.doCommand(key, (k) -> {
            redisTemplate.opsForList().set(k, index, value);
            return null;
        });
    }

    @Override
    public void ltrim(String key, int start, int stop) {
        this.doCommand(key, (k) -> {
            redisTemplate.opsForList().trim(k, start, stop);
            return null;
        });
    }

    @Override
    public <T> Long zdd(String key, double score, T member) {
        return 0L;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return 0L;
    }

    @Override
    public <T> Set<T> zrange(String key, Long start, Long end) {
        return null;
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zrangeWithScores(String key, Long start, Long end) {
        return null;
    }

    @Override
    public <T> Set<T> zrangeByScore(String key, double min, double max) {
        return null;
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    }

    @Override
    public Long zrem(String key, Object... member) {
        return 0L;
    }

    @Override
    public Object eval(byte[] script, String key, Object... arg) {
        return null;
    }

    @Override
    public Object evalSha(byte[] sha1, String key, Object... arg) {
        return null;
    }

    @Override
    public byte[] scriptLoad(byte[] script, String key) {
        return new byte[0];
    }

    @Override
    public Long sadd(String key, Object... member) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().add(k, member);
        });
    }

    @Override
    public Long scard(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().size(k);
        });
    }

    @Override
    public <T> Boolean sisMember(String key, T member) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().isMember(k, member);
        });
    }

    @Override
    public <T> Set<T> smembers(String key) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().members(k);
        });
    }

    @Override
    public <T> T spop(String key) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForSet().pop(k);
        });
    }

    @Override
    public <T> List<T> srandMember(String key, int count) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().randomMembers(k, count);
        });
    }

    @Override
    public <T> T srandMember(String key) {
        return this.doCommand(key, (k) -> {
            return (T) redisTemplate.opsForSet().randomMember(k);
        });
    }

    @Override
    public Long srem(String key, Object... member) {
        return this.doCommand(key, (k) -> {
            return redisTemplate.opsForSet().remove(k, member);
        });
    }


    /**
     * @return
     */
    @Override
    public String getNamespace() {
        String namespace = "";
        if (config.isNamespaceEnable()) {
            namespace = config.getNamespace();
        }
        if (null != namespace && namespace.trim().length() > 0) {
            namespace = namespace + ".";
        } else {
            namespace = "";
        }
        return namespace;
    }

    /**
     * @param key
     * @param callback
     * @param <T>
     * @return
     */
    private <T> T doCommand(String key, RedisCallback<T> callback) {
        Long begin = System.currentTimeMillis();
        String fullKey = key(key);
        try {
            return callback.callback(fullKey);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        } finally {
            if (config.getSlowLogSlowerThan() > 0
                    && (System.currentTimeMillis() - begin) > config.getSlowLogSlowerThan()) {
                Long useTime = System.currentTimeMillis() - begin;
                log.warn("execute redis command for key '{}' use time {}ms", fullKey, useTime);
            }
        }
    }


    /**
     * @param <T>
     */
    @FunctionalInterface
    interface RedisCallback<T> {
        /**
         * 处理回调
         *
         * @param key
         * @return
         * @throws Exception
         */
        T callback(String key) throws Exception;
    }

}
