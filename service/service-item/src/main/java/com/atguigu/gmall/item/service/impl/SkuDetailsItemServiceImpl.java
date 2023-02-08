package com.atguigu.gmall.item.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.atguigu.gmall.cache.annotation.GmallCache;
import com.atguigu.gmall.cache.service.CacheOpsService;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.Jsons;


import com.atguigu.gmall.feign.product.SkuDetailsItemFeignClient;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.item.service.SkuDetailsItemService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryView;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class SkuDetailsItemServiceImpl implements SkuDetailsItemService {
    @Autowired
    SkuDetailsItemFeignClient itemFeignClient;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    CacheOpsService cacheOpsService;

    @Autowired
    SearchFeignClient   searchFeignClient;

    Map<String,SkuDetailsTo> map = new ConcurrentHashMap<>();

    public SkuDetailsTo getSkuDetails1(Long skuId) {
        // SkuDetailsTo skuDetailsTo = itemFeignClient.getSkuDetails(skuId);
        SkuDetailsTo skuDetailsTo = new SkuDetailsTo();

        Result<SkuInfo> skuInfoResult = itemFeignClient.getSKuInfoById(skuId);
        SkuInfo skuInfo = skuInfoResult.getData();
        Long spuId = skuInfo.getSpuId();
        Long c3Id = skuInfo.getCategory3Id();

        Result<List<SpuSaleAttr>> listResult = itemFeignClient.getAttrAndValueBySkuId(skuId, spuId);
        List<SpuSaleAttr> spuSaleAttrList = listResult.getData();

        List<SkuImage> skuImageList = itemFeignClient.getImagesBySkuId(skuId).getData();
        skuInfo.setSkuImageList(skuImageList);

        CategoryView categoryView = itemFeignClient.getCategorySkuDetails(c3Id).getData();

        BigDecimal price = itemFeignClient.getPrice1010(skuId).getData();

        String valueJson = itemFeignClient.getValueJson(spuId).getData();

        skuDetailsTo.setSkuInfo(skuInfo);
        skuDetailsTo.setSpuSaleAttrList(spuSaleAttrList);
        skuDetailsTo.setPrice(price);
        skuDetailsTo.setValuesSkuJson(valueJson);
        skuDetailsTo.setCategoryView(categoryView);

        return skuDetailsTo;


    }

    //未加缓存
    public SkuDetailsTo fromToNoCache(Long skuId) {
        // SkuDetailsTo skuDetailsTo = itemFeignClient.getSkuDetails(skuId);
        SkuDetailsTo skuDetailsTo = new SkuDetailsTo();

        CompletableFuture<SkuInfo> skuInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> skuInfoResult = itemFeignClient.getSKuInfoById(skuId);
            SkuInfo skuInfo = skuInfoResult.getData();

            skuDetailsTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);

        CompletableFuture<Void> attrCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo !=null){

            Long spuId = skuInfo.getSpuId();
            Result<List<SpuSaleAttr>> listResult = itemFeignClient.getAttrAndValueBySkuId(skuId, spuId);
            List<SpuSaleAttr> spuSaleAttrList = listResult.getData();
            skuDetailsTo.setSpuSaleAttrList(spuSaleAttrList);
            }
        }, threadPoolExecutor);

        CompletableFuture<Void> imageCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo != null){

            List<SkuImage> skuImageList = itemFeignClient.getImagesBySkuId(skuId).getData();
            skuInfo.setSkuImageList(skuImageList);
            }
        }, threadPoolExecutor);

        CompletableFuture<Void> categoryCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo != null){

            Long c3Id = skuInfo.getCategory3Id();
            CategoryView categoryView = itemFeignClient.getCategorySkuDetails(c3Id).getData();
            skuDetailsTo.setCategoryView(categoryView);
            }
        }, threadPoolExecutor);

        CompletableFuture<Void> priceCompletableFuture = CompletableFuture.runAsync(() -> {
            BigDecimal price = itemFeignClient.getPrice1010(skuId).getData();
            skuDetailsTo.setPrice(price);
        });

        CompletableFuture<Void> valueJsonCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            if(skuInfo != null){

            Long spuId = skuInfo.getSpuId();
            String valueJson = itemFeignClient.getValueJson(spuId).getData();
            skuDetailsTo.setValuesSkuJson(valueJson);
            }
        }, threadPoolExecutor);

        //一起返回
        CompletableFuture.allOf(attrCompletableFuture,
                                attrCompletableFuture,
                                imageCompletableFuture,
                                categoryCompletableFuture,
                                priceCompletableFuture,
                                valueJsonCompletableFuture)
        .join(

        );
        return skuDetailsTo;


    }

    /**
     * 本地缓存
     * */

    public SkuDetailsTo getSkuDetails2(Long skuId) {

        SkuDetailsTo skuDetailsTo = map.get("skuDetailsTo");
        if(skuDetailsTo == null){
            SkuDetailsTo skuDetailsTo1 = fromToNoCache(skuId);
            map.put("skuDetailsTo",skuDetailsTo1);
            return skuDetailsTo1;
        }
        return skuDetailsTo;
    }

    /**
     * 引入redis缓存
     * */


    public SkuDetailsTo getSkuDetailsXXX(Long skuId) {
        String str = redisTemplate.opsForValue().get("sku:info:" + skuId);
        //空值缓存
        if("x".equals(str)){
            return null;

        }
        if(StringUtils.isEmpty(str)){
            String cacheJson = "x";
            SkuDetailsTo skuDetailsTo1 = fromToNoCache(skuId);
            if(skuDetailsTo1 != null){

            redisTemplate.opsForValue().set("sku:info:" + skuId,Jsons.toStr(skuDetailsTo1),7l,TimeUnit.DAYS);
            }
            redisTemplate.opsForValue().set("sku:info:" + skuId,cacheJson,30l, TimeUnit.MINUTES);
            return skuDetailsTo1;

        }
        //转SkuDetailsTo
        SkuDetailsTo skuDetailsTo = Jsons.toObj(str, SkuDetailsTo.class);
        return skuDetailsTo;
    }


    public SkuDetailsTo getSkuDetailsWith(Long skuId) {

        String cacheKey = SysRedisConst.SKU_INFO_CACHE_PREFIX + skuId;
        //1 从缓存中获取数据
        SkuDetailsTo skuDetailsTo = cacheOpsService.getCacheData(cacheKey,SkuDetailsTo.class);
        //2 判断
        if(skuDetailsTo == null){
          //3 缓存中没有

            //4 问布隆数据库是否命中
            boolean contains = cacheOpsService.bloomContatins(skuId);

            if(!contains){
                //5 布隆说无
                return null;
            }
        //6 布隆说有，可能有，加分布式锁
            boolean lock = cacheOpsService.tryLock(skuId);

            if(lock){
             //7 加锁成功,回源(数据库查询，放缓存)
                SkuDetailsTo data = fromToNoCache(skuId);
                log.info("准备回源....");
                //放缓存
                cacheOpsService.saveCache(cacheKey,data);

                cacheOpsService.unLock(skuId);


            }

            //8 加锁失败,睡一秒，继续查缓存
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           return cacheOpsService.getCacheData(cacheKey, SkuDetailsTo.class);

        }

        return skuDetailsTo;
    }

    @GmallCache(cacheKey = SysRedisConst.SKU_INFO_CACHE_PREFIX + "#{#params[0]}",
                bloomName = SysRedisConst.BLOOM_SKUID,
                bloomValue = "#{#params[0]}",
                lockName = SysRedisConst.LOCK_SKU_DETAIL + "#{#params[0]}",
                dataTtl = 60 * 60 * 24 * 7l)
    @Override
    public SkuDetailsTo getSkuDetails(Long skuId) {
        SkuDetailsTo skuDetailsTo = fromToNoCache(skuId);
        return skuDetailsTo;
    }

    @Override
    public void updateHotscore(Long skuId, Long score) {
        searchFeignClient.updateHotScore(skuId,score);
    }
}
