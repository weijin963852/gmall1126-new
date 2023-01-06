package com.atguigu.gmall.cache.service.impl;




import com.atguigu.gmall.cache.constant.SysRedisConst;
import com.atguigu.gmall.cache.service.CacheOpsService;
import com.atguigu.gmall.cache.utils.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * 缓存相关的
 */
@Service
public class CacheOpsServiceImpl implements CacheOpsService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;


    @Override
    public <T> T getCacheData(String cacheKey, Class<T> clz) {
        String s = redisTemplate.opsForValue().get(cacheKey);
        if (SysRedisConst.NULL_VAL.equals(s)) {
            return null;
        }
        T t = Jsons.toObj(s, clz);
        return t;
    }

    @Override
    public Object getCacheData(String cacheKey, Type type) {
        String s = redisTemplate.opsForValue().get(cacheKey);
        if (SysRedisConst.NULL_VAL.equals(s)) {
            return null;
        }
        Object o = Jsons.toObj(s, new TypeReference<Object>() {
            @Override
            public Type getType() {
                return type;
            }
        });
        return o;

    }

    @Override
    public boolean bloomContatins(Object skuId) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return bloomFilter.contains(skuId);
    }

    @Override
    public boolean bloomContatins(String bloomName, Object bloomValue) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomName);
        return bloomFilter.contains(bloomValue);

    }

    /**
     * 加分布式锁
     */
    @Override
    public boolean tryLock(Long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;
        RLock lock = redissonClient.getLock(lockKey);

        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        return lock.tryLock();

    }

    /**
     * 保存到缓存
     */
    @Override
    public void saveCache(String cacheKey, Object data) {
        if (data == null) {
            //数据库查出来空值
            redisTemplate.opsForValue().set(cacheKey, SysRedisConst.NULL_VAL, SysRedisConst.NULL_VAL_TTL, TimeUnit.SECONDS);
        }
        //查出来的数据非控制
        redisTemplate.opsForValue().set(cacheKey, Jsons.toStr(data), SysRedisConst.VAL_TTL, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     */
    @Override
    public void unLock(Long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;
        RLock lock = redissonClient.getLock(lockKey);

        lock.unlock();
    }

    @Override
    public void unLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
    }


}
