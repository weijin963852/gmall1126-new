package com.atguigu.gmall.model.to;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuDetailsTo {
    //商品分类
    private CategoryView categoryView;
    //sku详情
    private SkuInfo skuInfo;
    //sku对应的  spu 销售属性名和值的列表
    private List<SpuSaleAttr> spuSaleAttrList;

    private String valuesSkuJson;

    //实时价格
    private BigDecimal price;

}
