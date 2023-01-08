package com.atguigu.gmall.item.api;

import com.atguigu.gmall.common.result.Result;

import com.atguigu.gmall.item.service.SkuDetailsItemService;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inner/rpc/item")
public class SkuDetailsController {

    @Autowired
    SkuDetailsItemService detailsItemService;
    @GetMapping("/details/{skuId}")
    Result<SkuDetailsTo> getSkuDetails(@PathVariable("skuId") Long skuId){
        SkuDetailsTo skuDetailsTo=  detailsItemService.getSkuDetails(skuId);


        return Result.ok(skuDetailsTo);
    }

}
