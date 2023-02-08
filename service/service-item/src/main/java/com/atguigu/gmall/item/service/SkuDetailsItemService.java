package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailsTo;

public interface SkuDetailsItemService {
    SkuDetailsTo getSkuDetails(Long skuId);

    void updateHotscore(Long skuId, Long score);
}
