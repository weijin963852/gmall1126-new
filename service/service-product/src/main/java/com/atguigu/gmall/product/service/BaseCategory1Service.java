package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author admin
* @description 针对表【base_category1(一级分类表)】的数据库操作Service
* @createDate 2022-12-04 13:18:25
*/
public interface BaseCategory1Service extends IService<BaseCategory1> {

    List<CategoryTreeTo> getCategoryTree();
}
