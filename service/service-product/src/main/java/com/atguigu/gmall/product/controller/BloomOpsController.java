package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定期创建布隆过滤器
 * */
@RestController
@RequestMapping("/admin/product")
public class BloomOpsController {
    @Autowired
    BloomDataQueryService bloomDataQueryService;

    @Autowired
    BloomOpsService bloomOpsService;
    @GetMapping("/rebuild/now")
    public Result rebuildBloomFilter(){

        String bloomFilterName = SysRedisConst.BLOOM_SKUID;
        bloomOpsService.rebuildBloomFilter(bloomFilterName,bloomDataQueryService);

        return Result.ok();
    }
}
