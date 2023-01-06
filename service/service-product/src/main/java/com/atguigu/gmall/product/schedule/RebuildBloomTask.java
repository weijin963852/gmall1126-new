package com.atguigu.gmall.product.schedule;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RebuildBloomTask {

    @Autowired
    BloomDataQueryService dataQueryService;

    @Autowired
    BloomOpsService bloomOpsService;

    @Scheduled(cron = "* * * * * * ")
    public void rebulidBloom(){

        bloomOpsService.rebuildBloomFilter(SysRedisConst.BLOOM_SKUID,dataQueryService);
        log.info("布隆创建完成");
    }
}
