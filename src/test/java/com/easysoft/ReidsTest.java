package com.easysoft;

import com.easysoft.redis.IRedisOperater;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReidsTest {

    @Autowired
    private IRedisOperater redisOperater;


    @Test
    public void set() {
        redisOperater.set("user::27", "zyp");
    }

    @Test
    public void get() {
        log.info(redisOperater.get("user::27"));
    }

    @Test
    public void getSet() {
        Integer abc = redisOperater.getSet("abc", 9);
        log.info(abc.toString());
    }

    @Test
    public void getSetLong() {
        Long cc = redisOperater.getSetLong("cc", 9L);
        log.info(cc.toString());
    }

    @Test
    public void exists() {
        log.info(redisOperater.exists("user::28").toString());
    }

    @Test
    public void setex() {
        log.info(redisOperater.setex("user::27", "zyp", 10).toString());
    }

    @Test
    public void setnx() {
        log.info(redisOperater.setnx("user::27", "zyp", 100).toString());
    }

    @Test
    public void delete() {
        log.info(redisOperater.delete("user::27").toString());
    }

    @Test
    public void expire() {
        log.info(redisOperater.expire("user::27", 50).toString());
    }

    @Test
    public void ttl() {
        log.info(redisOperater.ttl("user::27").toString());
    }

    @Test
    public void getLong() {
        log.info(redisOperater.getLong("cc").toString());
    }

    @Test
    public void incr() {
        redisOperater.set("bb", 1);
        log.info(redisOperater.incr("bb").toString());
    }

    @Test
    public void incrBy() {
        redisOperater.set("bb", 1);
        log.info(redisOperater.incrBy("bb", 10086).toString());
    }

    @Test
    public void decr() {
        redisOperater.set("bb", 1);
        log.info(redisOperater.decr("bb").toString());
    }

    @Test
    public void decrBy() {
        redisOperater.set("bb", 1);
        log.info(redisOperater.decrBy("bb", 10086).toString());
        log.info(Integer.MAX_VALUE + "");
    }

    @Test
    public void hset() {
        redisOperater.hset("user1", "name", "zyp");
    }

    @Test
    public void hsetnx() {
        log.info(redisOperater.hsetnx("user1", "name", "zyp1").toString());
    }

    @Test
    public void hget() {
        log.info(redisOperater.hget("user1", "name"));
    }

    @Test
    public void hmset() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zyp");
        map.put("age", 12);
        redisOperater.hmset("user1", map);
    }

    @Test
    public void hgetAll() {
        redisOperater.hgetAll("user1").forEach((key, value) -> {
            log.info(key + ":" + value);
        });
    }

    @Test
    public void hmget() {
        redisOperater.hmget("user1", "name", "age").forEach((value) -> {
            log.info(value.toString());
        });
    }

    @Test
    public void hdel() {
        log.info(redisOperater.hdel("user1", "name", "age").toString());
    }

    @Test
    public void hincrBy() {
        log.info(redisOperater.hincrBy("user1", "age", 5).toString());
    }

    @Test
    public void hexists() {
        log.info(redisOperater.hexists("user1", "email").toString());
    }

    @Test
    public void lpush() {
        log.info(redisOperater.lpush("list", "email", "age", "name").toString());
    }

    @Test
    public void lpushx() {
        log.info(redisOperater.lpushx("list1", "email").toString());
    }

    @Test
    public void lpop() {
        log.info(redisOperater.lpop("list").toString());
    }

    @Test
    public void rpop() {
        log.info(redisOperater.rpop("list").toString());
    }

    @Test
    public void rpush() {
        log.info(redisOperater.rpush("list", "phonenumber").toString());
    }

    @Test
    public void lindex() {
        log.info(redisOperater.lindex("list", 1).toString());
    }

    @Test
    public void linsertAfter() {
        log.info(redisOperater.linsertAfter("list", "email", "email_after").toString());
    }

    @Test
    public void linsertBefore() {
        log.info(redisOperater.linsertBefore("list", "email", "email_before").toString());
    }

    @Test
    public void llen() {
        log.info(redisOperater.llen("list").toString());
    }

    @Test
    public void lrange() {
        log.info(redisOperater.lrange("list", 0, 10).toString());
    }

    @Test
    public void lrem() {
        redisOperater.delete("list");
        redisOperater.rpush("list", "age", "1", "age", "2", "age", "3");
        log.info(redisOperater.lrem("list", 2, "age").toString());
    }

    @Test
    public void lset() {
        redisOperater.lset("list", 9, "age");
    }

    @Test
    public void ltrim() {
        redisOperater.ltrim("list", 1, 2);
    }

    @Test
    public void sadd() {
        log.info(redisOperater.sadd("set", 1, 2, 33).toString());
    }

    @Test
    public void scard() {
        log.info(redisOperater.scard("set").toString());
    }

    @Test
    public void sisMember() {
        redisOperater.delete("set");
        redisOperater.sadd("set", 1, 2, 33);
        Assert.assertEquals(redisOperater.sisMember("set", 33), true);
        Assert.assertEquals(redisOperater.sisMember("set", 3), false);
    }

    @Test
    public void spop() {
        redisOperater.delete("set");
        redisOperater.sadd("set", 1, 2, 33);
        log.info(redisOperater.spop("set").toString());
    }

    @Test
    public void srandMember() {
        redisOperater.delete("set");
        redisOperater.sadd("set", 1, 2, 33);
        log.info(redisOperater.srandMember("set", 2).toString());
        log.info(redisOperater.srandMember("set").toString());
    }

    @Test
    public void srem() {
        redisOperater.delete("set");
        redisOperater.sadd("set", 1, 2, 33);
        log.info(redisOperater.srem("set", 2).toString());
    }

    @Test
    public void hello() {
        log.info(String.format("helloword", "zyp"));
    }


}
