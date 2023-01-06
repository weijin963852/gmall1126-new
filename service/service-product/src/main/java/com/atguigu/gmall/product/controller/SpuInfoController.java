package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttrValue;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *spu平台属性
*/
@RestController
@Api(tags = "spu管理")
@RequestMapping("/admin/product/")
public class SpuInfoController {
    @Autowired
    SpuInfoService spuInfoService;

    @Autowired
    SpuImageService spuImageService;

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @Autowired
    SpuSaleAttrValueService spuSaleAttrValueService;
    /**
     *
     *分页查询spu /product/1/10?category3Id=61
    */
    @GetMapping("/{pn}/{size}")
    public Result getSpuInfoList(@PathVariable("pn")Long pn,
                                 @PathVariable("size")Long size,
                                 @RequestParam("category3Id")Long category3Id){
        Page<SpuInfo> page = new Page<>(pn,size);
        LambdaQueryWrapper<SpuInfo> query = new LambdaQueryWrapper<>();
        query.eq(SpuInfo::getCategory3Id,category3Id);
        spuInfoService.page(page,query);
        return Result.ok(page);

    }

    /**
     * 新增spu表,图片,销售属性名列表
     * /admin/product/saveSpuInfo
     * */

    @Transactional
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        //保存spuinfo
        spuInfoService.save(spuInfo);

        Long spuId = spuInfo.getId();

        //保存图片
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuId);
        }
        spuImageService.saveBatch(spuImageList);

        //保存spu销售属性名
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuId);

            //保存销售属性值
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuId);
                spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
            }
            spuSaleAttrValueService.saveBatch(spuSaleAttrValueList);
        }

        spuSaleAttrService.saveBatch(spuSaleAttrList);
        return Result.ok();
    }

    /**
     * 查询某个spuId销售属性名和值的 admin/product/spuSaleAttrList/28
     * */
    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result getSpuSaleAttrListById(@PathVariable("spuId")Long spuId){

       List<SpuSaleAttr> spuSaleAttrs = spuInfoService.getSpuSaleAttrListById(spuId);
       return Result.ok(spuSaleAttrs);
    }
}
