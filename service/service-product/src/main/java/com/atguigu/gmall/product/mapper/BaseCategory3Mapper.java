package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;


import java.util.List;

/**
* @author admin
* @description 针对表【base_category3(三级分类表)】的数据库操作Mapper
* @createDate 2022-11-24 22:41:25
* @Entity com.atguigu.gmall.product.domain.BaseCategory3
*/
public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {

    List<BaseCategory3> selectCategory3ByC2Id(@Param("c2Id") Long c2Id);

    List<BaseAttrInfo> getAttrNameAndVale(@Param("c1Id") Long c1Id, @Param("c2Id") Long c2Id, @Param("c3Id") Long c3Id);
}




