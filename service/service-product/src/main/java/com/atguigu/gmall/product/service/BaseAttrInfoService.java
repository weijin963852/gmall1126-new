package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author admin
* @description 针对表【base_attr_info(属性表)】的数据库操作Service
* @createDate 2022-11-27 19:51:46
*/
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {

    List<BaseAttrInfo> getAttrNameAndVale(Long c1Id, Long c2Id, Long c3Id);

    void saveAttrNameAndValue(BaseAttrInfo attrInfo);
}
