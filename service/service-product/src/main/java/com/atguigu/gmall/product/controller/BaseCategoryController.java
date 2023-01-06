package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "商品分类")
@RestController
@RequestMapping("/admin/product")
public class BaseCategoryController {

    @Autowired
    private BaseCategory1Service category1Service;

    @Autowired
    private BaseCategory2Service category2Service;

    @Autowired
    private BaseCategory3Service category3Service;
    /**  admin/product/getCategory1
        所有一级分类列表

    */
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> category1List = category1Service.list();
        return Result.ok(category1List);
    }

    /**
     * 查询某个二级分类下的所有一级分类
     * */
    @GetMapping("/getCategory2/{category1}")
    public Result category2(@PathVariable("category1") Long category1){
        LambdaQueryWrapper<BaseCategory2> query = new LambdaQueryWrapper<>();
        query.eq(BaseCategory2::getCategory1Id,category1);
        List<BaseCategory2> category2List = category2Service.list(query);
        return Result.ok(category2List);

    }

    /**
     * 某个二级分类下所有的三级分类 /admin/product/getCategory3/25
     * */

    @GetMapping("/getCategory3/{c2Id}")
    public Result<List<BaseCategory3>> category3(@PathVariable("c2Id")Long c2Id){
     /*   LambdaQueryWrapper<BaseCategory3> query = new LambdaQueryWrapper<>();
        query.eq(BaseCategory3::getCategory2Id,c2Id);
        List<BaseCategory3> category3List = category3Service.list(query);*/
        List<BaseCategory3> category3List = category3Service.selectCategory3ByC2Id(c2Id);
        return Result.ok(category3List);
    }
}
