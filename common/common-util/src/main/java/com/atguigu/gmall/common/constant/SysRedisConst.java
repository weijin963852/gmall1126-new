package com.atguigu.gmall.common.constant;

public class SysRedisConst {

    public static final String NULL_VAL = "x";

    public static final String LOCK_SKU_DETAIL = "lock:sku:detail:";

    //空值缓存过期时间30分钟
    public static final Long NULL_VAL_TTL = 60 * 30L;

    //正常值的过期时间 7天
    public static final Long VAL_TTL = 60 * 60 * 24 * 7L;


    public static final String SKU_INFO_CACHE_PREFIX = "sku:info:";

    public static final String BLOOM_SKUID = "bloom:skuId";

    //新的布隆过滤器
    public static final String NEW_BLOOM = "new:bloom";

    public static final String CACHE_PREFIX_CATEGORY = "category:key:" ;
}
