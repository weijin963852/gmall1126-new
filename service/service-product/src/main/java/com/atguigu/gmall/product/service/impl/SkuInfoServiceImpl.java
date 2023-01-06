package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}




