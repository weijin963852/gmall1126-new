package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author admin
* @description 针对表【base_attr_value(属性值表)】的数据库操作Service
* @createDate 2022-11-27 19:51:46
*/
public interface BaseAttrValueService extends IService<BaseAttrValue> {

    void updateAttrNameAndValue(BaseAttrInfo attrInfo);
}
