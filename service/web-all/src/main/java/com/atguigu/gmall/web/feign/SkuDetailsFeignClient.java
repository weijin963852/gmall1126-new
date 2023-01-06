package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-item")
@RequestMapping("/api/inner/rpc/item")
public interface SkuDetailsFeignClient {
    @GetMapping("/details/{skuId}")
    Result<SkuDetailsTo> getSkuDetails(@PathVariable("skuId") Long skuId);
}
