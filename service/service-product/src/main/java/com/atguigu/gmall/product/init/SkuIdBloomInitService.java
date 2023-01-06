package com.atguigu.gmall.product.init;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SkuIdBloomInitService {
    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SkuInfoService skuInfoService;
    @PostConstruct  //组件创建后，初始化布隆过滤器
    public void initSkuBloom(){
        //1 查询sku所有的商品id,数据量太大可以分页查询
       List<Long> ids = skuInfoService.selectFindAllId();

       //2 初始化布隆过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);

        //3 初始化布隆过滤器
        boolean exists = bloomFilter.isExists();
        if(!exists){
            //尝试初始化。如果布隆过滤器没有初始化过，就尝试初始化
            bloomFilter.tryInit(5000000,0.00001);

        }

        //4 id添加到布隆过滤器里
        for (Long id : ids) {
            bloomFilter.add(id);
        }
    }
}
