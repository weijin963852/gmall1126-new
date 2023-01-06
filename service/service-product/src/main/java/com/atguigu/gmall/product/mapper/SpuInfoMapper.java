package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【spu_info(商品表)】的数据库操作Mapper
* @createDate 2022-11-27 19:51:47
* @Entity com.atguigu.gmall.product.domain.SpuInfo
*/
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {

    List<SpuSaleAttr> getSpuSaleAttrListById(@Param("spuId") Long spuId);
}




