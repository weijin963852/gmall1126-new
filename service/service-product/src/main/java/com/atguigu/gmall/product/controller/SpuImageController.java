package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.product.service.SpuImageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图片信息
 * */
@RestController
@RequestMapping("/admin/product")
public class SpuImageController {

    @Autowired
    SpuImageService spuImageService;
    /**
     *
     *查询某个spu下的所有图片
    */
    @GetMapping("/spuImageList/{spuId}")
    public Result getSpuImageListById(@PathVariable("spuId")Long spuId){
        LambdaQueryWrapper<SpuImage> query = new LambdaQueryWrapper<>();
        query.eq(SpuImage::getSpuId,spuId);
        List<SpuImage> spuImages = spuImageService.list(query);
        return Result.ok(spuImages);

    }
}
