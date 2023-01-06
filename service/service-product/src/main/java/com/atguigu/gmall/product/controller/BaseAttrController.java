package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台属性相关的controller
 * */
@Api(tags = "平台属性")
@RestController
@RequestMapping("/admin/product")
public class BaseAttrController {
    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService baseAttrValueService;
    /**
     *
     *查询一级，二级，三级分类下的平台属性
    */
    @ApiOperation(value = "获取属性名和值")
    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result<List<BaseAttrInfo>> getAttrNameAndVale(@ApiParam("一级分类id") @PathVariable("c1Id")Long c1Id,
                                     @PathVariable("c2Id")Long c2Id,
                                     @PathVariable("c3Id")Long c3Id){

        List<BaseAttrInfo> listAttr = baseAttrInfoService.getAttrNameAndVale(c1Id,c2Id,c3Id);
        return Result.ok(listAttr);
    }

    /**
     *
     * 保存和修改属性名和值
     * */

    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo attrInfo){
        if(attrInfo.getId() == null){

        baseAttrInfoService.saveAttrNameAndValue(attrInfo);
        return Result.ok();
        }else {
            baseAttrValueService.updateAttrNameAndValue(attrInfo);
            return Result.ok();
        }

    }


    /**
     * 根据属性id，查询所有的属性值
     * */
    @GetMapping("/getAttrValueList/{attrId}")
    public Result<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId")Long attrId ){
        LambdaQueryWrapper<BaseAttrValue> query = new LambdaQueryWrapper<>();
        query.eq(BaseAttrValue::getAttrId,attrId);
        List<BaseAttrValue> valueList = baseAttrValueService.list(query);
        return Result.ok(valueList);

    }



}
