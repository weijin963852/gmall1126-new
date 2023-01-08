package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.feign.product.CategoryFeignClient;
import com.atguigu.gmall.model.to.CategoryTreeTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    CategoryFeignClient categoryFeignClient;
    @GetMapping("/")
    public String index(Model model){
        List<CategoryTreeTo> treeTos = categoryFeignClient.categoryTree().getData();
        if(treeTos != null && treeTos.size() > 0){
            model.addAttribute("list",treeTos);
        }

        return "index/index";
    }
}
