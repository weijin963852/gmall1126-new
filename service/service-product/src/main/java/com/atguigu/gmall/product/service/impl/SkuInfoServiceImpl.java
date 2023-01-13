package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.CategoryView;
import com.atguigu.gmall.product.mapper.*;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author admin
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2022-11-27 19:51:46
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService{

    @Autowired
    SkuInfoMapper skuInfoMapper;

    @Autowired
    BaseTrademarkMapper baseTrademarkMapper;

    @Autowired
    BaseCategory1Mapper category1Mapper;

    @Autowired
    SkuAttrValueMapper attrValueMapper;
    @Override
    public void getChangeSkuInfoIsSale(Long skuId, Integer i) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(i);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void onSale(Long skuId, int i) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(i);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public List<Long> selectFindAllId() {

        return skuInfoMapper.selectFindAllId();
    }

    /**
     * 通过id获取Goods
     * */
    @Override
    public Goods getGoodsBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);


        Goods goods = new Goods();

        goods.setId(skuId);
        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setTitle(skuInfo.getSkuName());
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setCreateTime(new Date());
        goods.setTmId(skuInfo.getTmId());

        BaseTrademark baseTrademark = baseTrademarkMapper.selectById(skuInfo.getTmId());
        goods.setTmName(baseTrademark.getTmName());
        goods.setTmLogoUrl(baseTrademark.getLogoUrl());

        CategoryView categoryView = category1Mapper.getCategorySkuDetails(skuInfo.getCategory3Id());
        goods.setCategory1Id(categoryView.getCategory1Id());
        goods.setCategory1Name(categoryView.getCategory1Name());
        goods.setCategory2Id(categoryView.getCategory2Id());
        goods.setCategory2Name(categoryView.getCategory2Name());
        goods.setCategory3Id(categoryView.getCategory3Id());
        goods.setCategory3Name(categoryView.getCategory3Name());

        //热度分更新
        goods.setHotScore(0l);

        //查询sku所有平台属性名和值
        List<SearchAttr> attrs = attrValueMapper.getSearchAttrListBySkuId(skuId);
        goods.setAttrs(attrs);
        return goods;


    }
}




