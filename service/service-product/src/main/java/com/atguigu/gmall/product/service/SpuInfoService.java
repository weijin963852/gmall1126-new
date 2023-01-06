package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author admin
* @description 针对表【spu_info(商品表)】的数据库操作Service
* @createDate 2022-11-27 19:51:47
*/
public interface SpuInfoService extends IService<SpuInfo> {

    List<SpuSaleAttr> getSpuSaleAttrListById(Long spuId);
}
