package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryView;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import com.atguigu.gmall.model.to.ValueJsonTo;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.product.service.SkuDetailsService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.impl.SkuDetailsServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取商品详情页需要的数据
 * */
@RestController
@RequestMapping("/api/inner/rpc/product")

public class SkuDetailsController {
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuDetailsService detailsService;
    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    BaseCategory1Mapper category1Mapper;

    @PostMapping("/details1/{skuId}")
    SkuDetailsTo getSkuDetails(@PathVariable("skuId") Long skuId){
        SkuDetailsTo SkuDetailsTo = detailsService.getSkuDetails(skuId);

        return SkuDetailsTo;
    }

    /**
     * 根据skuId查询 SkuInfo
     * */
    @GetMapping("/details/{skuId}")
    public Result<SkuInfo> getSKuInfoById(@PathVariable("skuId")Long skuId){
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return Result.ok(skuInfo);
    }
    /**
     * 查询某个skuId 下的图片信息
     * */
    @GetMapping("/details/images/{skuId}")
    public Result<List<SkuImage>> getImagesBySkuId(@PathVariable("skuId")Long skuId){
        LambdaQueryWrapper<SkuImage> query = new LambdaQueryWrapper<>();
        query.eq(SkuImage::getSkuId,skuId);
        List<SkuImage> skuImages = skuImageService.list(query);
        return Result.ok(skuImages);

    }
    /**
     *  查询skuId对应的 spu销售属性名和值
     * */
    @GetMapping("/details/getAttrAndValueBySkuId/{skuId}/{spuId}")
    public Result<List<SpuSaleAttr>> getAttrAndValueBySkuId(@PathVariable("skuId")Long skuId,@PathVariable("spuId")Long spuId){


        List<SpuSaleAttr> saleAttrAndValeMarkSku = spuSaleAttrMapper.getSaleAttrAndValeMarkSku(spuId, skuId);
        return Result.ok(saleAttrAndValeMarkSku);
    }

    /**
     * 查询sku商品具体的分类
     * */
    @GetMapping("/details/getCategorySkuDetails/{c3Id}")
    public Result<CategoryView> getCategorySkuDetails(@PathVariable("c3Id")Long c3Id){

        CategoryView categoryView = category1Mapper.getCategorySkuDetails(c3Id);
        return Result.ok(categoryView);
    }
    /**
     * 查询商品实时价格
     * */

    @GetMapping("/details/getPrice1010/{skuId}")
    public Result<BigDecimal> getPrice1010(@PathVariable("skuId")Long skuId){
        BigDecimal price = skuInfoMapper.get1010Price(skuId);
        return Result.ok(price);
    }

    /**
     * 查询ValuesSkuJson
     * */

    @GetMapping("/details/getValueJson/{spuId}")
    public Result<String> getValueJson(@PathVariable("spuId")Long spuId){
        List<ValueJsonTo> valueJsonTos = spuSaleAttrMapper.getValuesSkuJson(spuId);
        Map<String,Long> map = new HashMap<>();
        for (ValueJsonTo valueJsonTo : valueJsonTos) {
            Long skuId1 = valueJsonTo.getSkuId();
            String valueJson = valueJsonTo.getValueJson();
            map.put(valueJson,skuId1);
        }
        //Map --->成json
        String s = Jsons.toStr(map);
        return Result.ok(s);
    }
}
