package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailsTo;
import com.atguigu.gmall.web.feign.SkuDetailsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    SkuDetailsFeignClient detailsFeignClient;
    @GetMapping("/{skuId}.html")
    public String item(@PathVariable("skuId")Long skuId, Model model){

        Result<SkuDetailsTo> details = detailsFeignClient.getSkuDetails(skuId);
        if(details.isOk()){

            if(details.getData() == null && details.getData().getSkuInfo() == null){
                //商品不存在
                return "item/error";
            }
            SkuDetailsTo data = details.getData();
            model.addAttribute("categoryView",data.getCategoryView());
            model.addAttribute("skuInfo",data.getSkuInfo());
            model.addAttribute("price",data.getPrice());
            model.addAttribute("spuSaleAttrList",data.getSpuSaleAttrList());
            model.addAttribute("valuesSkuJson",data.getValuesSkuJson());
        }
        return "item/index";

    }
}
