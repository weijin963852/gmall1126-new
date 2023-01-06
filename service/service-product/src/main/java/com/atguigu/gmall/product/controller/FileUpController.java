package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.FileUpService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传
 * /admin/product/fileUpload
 * */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/admin/product/")
public class FileUpController {
    @Autowired
    FileUpService fileUpService;
    @PostMapping("/fileUpload")
    public Result fileUp(@RequestPart("file")MultipartFile file) throws Exception{
        String url= fileUpService.upFile(file);
        return Result.ok(url);
    }

    /**
     * 测试接受各种参数
     * RequestPart:接受文件
         @RequestParam Map allMap 接受所有普通的参数，不含文件
     * */
    @PostMapping("/test")
    public Result test(@RequestParam("username")String username,
                       @RequestParam("password")String password,
                       @RequestParam("email")String email,
                       @RequestPart("sfz")MultipartFile[] sfz,
                       @RequestPart("header")MultipartFile header,
                       @RequestParam("ah")String[] hobby,
                       @RequestParam("ah")String ah,
                       @RequestParam Map allMap){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("email",email);

        System.out.println("map = " + map);
        for(MultipartFile file : sfz){
            System.out.println(file.getSize());
        }

        System.out.println();
        System.out.println(header.getOriginalFilename());
        System.out.println(Arrays.toString(hobby));
        System.out.println(ah);

        return Result.ok();
    }


}
