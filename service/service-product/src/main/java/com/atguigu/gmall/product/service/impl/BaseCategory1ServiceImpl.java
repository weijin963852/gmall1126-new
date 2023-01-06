package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author admin
* @description 针对表【base_category1(一级分类表)】的数据库操作Service实现
* @createDate 2022-12-04 13:18:25
*/
@Service
public class BaseCategory1ServiceImpl extends ServiceImpl<BaseCategory1Mapper, BaseCategory1>
    implements BaseCategory1Service{

    @Autowired
    BaseCategory1Mapper category1Mapper;
    @Override
    public List<CategoryTreeTo> getCategoryTree() {
        return category1Mapper.getCategoryTree();

    }
}




