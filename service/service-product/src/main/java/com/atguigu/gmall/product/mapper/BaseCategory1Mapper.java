package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.model.to.CategoryView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【base_category1(一级分类表)】的数据库操作Mapper
* @createDate 2022-12-04 13:18:25
* @Entity com.atguigu.gmall.product.domain.BaseCategory1
*/
public interface BaseCategory1Mapper extends BaseMapper<BaseCategory1> {

    List<CategoryTreeTo> getCategoryTree();

    CategoryView getCategorySkuDetails(@Param("category3Id") Long category3Id);
}




