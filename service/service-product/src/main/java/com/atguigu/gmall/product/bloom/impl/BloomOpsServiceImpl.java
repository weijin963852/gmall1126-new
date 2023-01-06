package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloomOpsServiceImpl implements BloomOpsService {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SkuInfoService skuInfoService;
    /**
     * 创建布隆过滤器
     * */
    @Override
    public void rebuildBloomFilter(String bloomFilterName, BloomDataQueryService bloomDataQueryService) {
        //1 查询所有的id
        List<Long> ids = bloomDataQueryService.selectAllId();

        //2 初始化新的布隆
        RBloomFilter<Object> newBloomFilter = redissonClient.getBloomFilter(SysRedisConst.NEW_BLOOM);

        //3 初始化布隆过滤器
        newBloomFilter.tryInit(5000000,0.00001);
        for (Long skuId : ids) {
            newBloomFilter.add(skuId);
        }

        //4 替换原来旧的布隆过滤器
        RBloomFilter<Object> oldBloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        oldBloomFilter.rename("bloom:temp");
        newBloomFilter.rename(SysRedisConst.BLOOM_SKUID);

        //5删除旧的布隆过滤器
        oldBloomFilter.deleteAsync();

    }
}
