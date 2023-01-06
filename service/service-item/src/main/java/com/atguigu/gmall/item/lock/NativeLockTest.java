package com.atguigu.gmall.item.lock;

import com.atguigu.gmall.common.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本地锁并发测试
 * */
@RestController
@RequestMapping("/lock")
public class NativeLockTest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/native")
    public synchronized Result test1(){
        String a = redisTemplate.opsForValue().get("a");
        int i = Integer.parseInt(a);
        i++;
        redisTemplate.opsForValue().set("a",String.valueOf(i));

        System.out.println("i = " + i);
        return null;
    }
}
