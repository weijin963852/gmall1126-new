package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
* @author admin
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-11-27 19:51:46
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void getChangeSkuInfoIsSale(Long skuId, Integer i);

    void onSale(Long skuId, int i);

    List<Long> selectFindAllId();
}
