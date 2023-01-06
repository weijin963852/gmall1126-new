package com.atguigu.gmall.product.init;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/bloomFilter/{skuId}")
    public Result bloomFilterTest(@PathVariable("skuId")Long skuId){
        // 1 获取布隆过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);

        boolean contains = bloomFilter.contains(skuId);
        return Result.ok(contains);
    }
}
