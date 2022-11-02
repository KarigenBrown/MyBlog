package me.blog.framework.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Karigen B
 * @create 2022-11-02 19:32
 */
@Component
public class RedisCacheUtils {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public Object getCacheObject(final String key) {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public boolean deleteCacheObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Long deleteCacheObjects(final Collection<Object> collection) {
        return redisTemplate.delete(collection);
    }

    public long setCacheList(final String key, final List<?> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    public List<?> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public BoundSetOperations<?, ?> setCacheSet(final String key, final Set<?> dataSet) {
        BoundSetOperations<Object, Object> setOperation = redisTemplate.boundSetOps(key);
        for (Object o : dataSet) {
            setOperation.add(o);
        }
        return setOperation;
    }

    public Set<?> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void setCacheMap(final String key, final Map<String, ?> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    public Map<Object, Object> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<Object, Object, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    public void delCacheMapValue(final String key, final String hKey) {
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hKey);
    }

    public List<Object> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    public Collection<Object> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
