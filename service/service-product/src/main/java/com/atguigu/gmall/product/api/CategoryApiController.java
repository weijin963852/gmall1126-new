package com.atguigu.gmall.product.api;

import com.atguigu.gmall.cache.annotation.GmallCache;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内部调用 分类相关的
 * */
@RestController
@RequestMapping("/api/inner/rpc/product")
public class CategoryApiController {

    @Autowired
    BaseCategory1Service category1Service;

    @GmallCache(cacheKey = SysRedisConst.CACHE_PREFIX_CATEGORY )
    @GetMapping("/category/tree")
    public Result<List<CategoryTreeTo>> categoryTree(){

        List<CategoryTreeTo> categoryTreeTos = category1Service.getCategoryTree();

        return Result.ok(categoryTreeTos);
    }
}
