package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.model.to.CategoryView;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import com.atguigu.gmall.model.to.ValueJsonTo;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.SkuDetailsService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkuDetailsServiceImpl implements SkuDetailsService {
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    BaseCategory1Mapper category1Mapper;
    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Override
    public SkuDetailsTo getSkuDetails(Long skuId) {
        SkuDetailsTo skuDetailsTo = new SkuDetailsTo();
        //1查询sku的三级分类id和skuInfo
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        Long spuId = skuInfo.getSpuId();

        //2 查询sku商品具体的分类
        CategoryView categoryView = category1Mapper.getCategorySkuDetails(skuInfo.getCategory3Id());

        //3 查询skuId对应的 spu销售属性名和值
 /*       List<SpuSaleAttr> spuSaleAttrs = spuInfoMapper.getSpuSaleAttrListById(skuInfo.getSpuId());
        //标记值如果有对应的sku和spu值，值的属性is_checked标记为1  名字中包含125GB 或所选颜色才能访问
        String skuName = skuInfo.getSkuName();
        if(spuSaleAttrs != null &&spuSaleAttrs.size() > 0 && !StringUtils.isEmpty(skuName)){
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrs) {
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                    if(skuName.contains(spuSaleAttrValue.getSaleAttrValueName()) ){
                        spuSaleAttrValue.setIsChecked("1");
                    }
                }
            }
        }*/
        List<SpuSaleAttr> saleAttrList = spuSaleAttrMapper.getSaleAttrAndValeMarkSku(spuId,skuId);


        //4 sku对应的图片
        LambdaQueryWrapper<SkuImage> query = new LambdaQueryWrapper<>();
        query.eq(SkuImage::getSkuId,skuId);
        List<SkuImage> skuImages = skuImageMapper.selectList(query);
        skuInfo.setSkuImageList(skuImages);

        //5 查询实时价格
       BigDecimal price = skuInfoMapper.get1010Price(skuId);

       //6 查询SkuDetailsTo 的valuesSkuJson
        String valuesSkuJson = getValuesSkuJson(spuId);

        skuDetailsTo.setValuesSkuJson(valuesSkuJson);
        skuDetailsTo.setSkuInfo(skuInfo);
        skuDetailsTo.setCategoryView(categoryView);
        skuDetailsTo.setSpuSaleAttrList(saleAttrList);
        skuDetailsTo.setPrice(price);
        return skuDetailsTo;
    }

    public String getValuesSkuJson(Long spuId) {
        //"118|1120:50"
        List<ValueJsonTo> valueJsonTos = spuSaleAttrMapper.getValuesSkuJson(spuId);
        Map<String,Long> map = new HashMap<>();
        for (ValueJsonTo valueJsonTo : valueJsonTos) {
            Long skuId1 = valueJsonTo.getSkuId();
            String valueJson = valueJsonTo.getValueJson();
            map.put(valueJson,skuId1);
        }
        //Map --->成json
        String s = Jsons.toStr(map);
        return s;
    }
}
