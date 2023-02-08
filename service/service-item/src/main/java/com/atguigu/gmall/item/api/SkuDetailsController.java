package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.constant.RedisConst;
import com.atguigu.gmall.common.result.Result;

import com.atguigu.gmall.item.service.SkuDetailsItemService;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inner/rpc/item")
public class SkuDetailsController {

    @Autowired
    SkuDetailsItemService detailsItemService;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/details/{skuId}")
    Result<SkuDetailsTo> getSkuDetails(@PathVariable("skuId") Long skuId){
        SkuDetailsTo skuDetailsTo=  detailsItemService.getSkuDetails(skuId);

        //更新热度分,高并发提高效率，可以规定 点击100次更新一次热度分
        Long score = fromRedisGetScore(skuId);
        if(score % 100 == 0){

        detailsItemService.updateHotscore(skuId,score);
        }
        return Result.ok(skuDetailsTo);
    }

    private Long fromRedisGetScore(Long skuId) {

        Long increment = redisTemplate.opsForValue().increment(RedisConst.UPDATE_HOT_SCORE + skuId );

        return increment;

    }

}
