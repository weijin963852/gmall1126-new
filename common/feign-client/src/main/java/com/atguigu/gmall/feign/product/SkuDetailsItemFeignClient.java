package com.atguigu.gmall.feign.product;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryView;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("service-product")
@RequestMapping("/api/inner/rpc/product")

public interface SkuDetailsItemFeignClient {
    @PostMapping("/details1/{skuId}")
    SkuDetailsTo getSkuDetails1(@PathVariable("skuId") Long skuId);

    /**
     * 根据skuId查询 SkuInfo
     * */
    @GetMapping("/details/{skuId}")
    public Result<SkuInfo> getSKuInfoById(@PathVariable("skuId")Long skuId);

    /**
     * 查询某个skuId 下的图片信息
     * */
    @GetMapping("/details/images/{skuId}")
    public Result<List<SkuImage>> getImagesBySkuId(@PathVariable("skuId")Long skuId);


    /**
     *  查询skuId对应的 spu销售属性名和值
     * */
    @GetMapping("/details/getAttrAndValueBySkuId/{skuId}/{spuId}")
    public Result<List<SpuSaleAttr>> getAttrAndValueBySkuId(@PathVariable("skuId")Long skuId,@PathVariable("spuId")Long spuId);

    /**
     * 查询sku商品具体的分类
     * */
    @GetMapping("/details/getCategorySkuDetails/{c3Id}")
    public Result<CategoryView> getCategorySkuDetails(@PathVariable("c3Id")Long c3Id);

    /**
     * 查询商品实时价格
     * */

    @GetMapping("/details/getPrice1010/{skuId}")
    public Result<BigDecimal> getPrice1010(@PathVariable("skuId")Long skuId);


    /**
     * 查询ValuesSkuJson
     * */

    @GetMapping("/details/getValueJson/{spuId}")
    public Result<String> getValueJson(@PathVariable("spuId")Long spuId);
}
