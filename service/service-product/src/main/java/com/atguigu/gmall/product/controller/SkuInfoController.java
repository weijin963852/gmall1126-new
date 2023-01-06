package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * skuinfo信息
 * */
@RestController
@RequestMapping("/admin/product")
public class SkuInfoController {

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImageService skuImageService;

    @Autowired
    SkuAttrValueService skuAttrValueService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    RedissonClient redissonClient;
    /**
     * 分页查询skuinfo
     * admin/product/list/1/10
     *
     *
    */
    @GetMapping("/list/{pn}/{size}")
    public Result getPageSkuInfoList(@PathVariable("pn")Long pn,
                                     @PathVariable("size")Long size){
        Page<SkuInfo> page = new Page<>(pn,size);
        skuInfoService.page(page,null);
        return Result.ok(page);
    }

    /**
     * 新增skuinfo /admin/product/saveSkuInfo 操作4张表
     * */
    @Transactional
    @PostMapping("/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
        //1保存skuInfo
        skuInfoService.save(skuInfo);
        Long id = skuInfo.getId();

        //2 保存skuImage
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(id);
        }
        skuImageService.saveBatch(skuImageList);
        //3 保存saleAttr
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(id);
        }
        skuAttrValueService.saveBatch(skuAttrValueList);

        //4 保存
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(id);
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
        }
        skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);


        //5 新增id的时候，直接在布隆里放id
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        bloomFilter.add(skuInfo.getId());
        return Result.ok();
    }

    /**
     * 商品下架 admin/product/cancelSale/40  is_sale 0
     * */
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){

        skuInfoService.getChangeSkuInfoIsSale(skuId,0);
        return Result.ok();
    }

    /**
     * 商品上架 admin/product/cancelSale/40  is_sale 0
     * */
    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){

        skuInfoService.onSale(skuId,1);
        return Result.ok();
    }
}
