package com.atguigu.gmall.item;

import com.atguigu.gmall.common.annotation.EnableThreadPool;
import com.atguigu.gmall.common.config.RedissionAutoConfiguration;
import com.atguigu.gmall.common.config.Swagger2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringCloudApplication
@EnableFeignClients(basePackages = "com.atguigu.gmall.feign.product")
@EnableThreadPool
@Import({Swagger2Config.class})
@EnableAspectJAutoProxy
public class ItemMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemMainApplication.class,args);
    }
}
