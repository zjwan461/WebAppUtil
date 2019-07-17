package com.itsu.utils;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.SerializationUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 苏犇
 * @date 2019/7/9 22:40
 */

public class JedisUtil {

    private static JedisUtil jedisUtil;

    private RedisTemplate redisTemplate;

    private JedisUtil(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public static JedisUtil getInstance(RedisTemplate redisTemplate) {
        if (jedisUtil == null) {
            jedisUtil = new JedisUtil(redisTemplate);
        }
        return jedisUtil;
    }

    private boolean isStrRedisTemplate() {
        if (this.redisTemplate instanceof StringRedisTemplate)
            return true;
        return false;
    }

    public Object get(String key) {
        if (isStrRedisTemplate()) {
            return redisTemplate.opsForValue().get(key);
        } else {
            return SerializationUtils.deserialize((byte[]) redisTemplate.opsForValue().get(key));
        }
    }

    public void set(String key, Object value) {
        if (isStrRedisTemplate()) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, SerializationUtils.serialize(value));
        }
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public void expir(String key) {
        expir(key, 1800);
    }

    public void expir(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public Set<String> keys(String keyPatten) {
        Set keys = redisTemplate.keys(keyPatten);
        return keys;
    }

    public void flushDB() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
    }
}
