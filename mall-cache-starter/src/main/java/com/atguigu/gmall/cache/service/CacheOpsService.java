package com.atguigu.gmall.cache.service;



import java.lang.reflect.Type;

public interface CacheOpsService {
    /**
     * 从缓存中获取数据
     * */
    <T>T getCacheData(String cacheKey, Class<T> clz);

    Object getCacheData(String cacheKey, Type type);

    boolean bloomContatins(Object skuId);

    boolean bloomContatins(String bloomName,Object bloomValue);

    boolean tryLock(Long skuId);

    boolean tryLock(String lockName);

    void saveCache(String cacheKey, Object data);

    void saveCache(String cacheKey, Object data,Long dataTtl);

    void unLock(Long  skuId);

    void unLock(String  lockName);

    /**
     * 延迟双删
     * */
    void delay2Delete(String cacheKey);
}
