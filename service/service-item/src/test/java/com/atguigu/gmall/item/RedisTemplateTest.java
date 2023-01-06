package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    void test(){
        redisTemplate.opsForValue().set("hello","world");

        System.out.println("保存成功");

        String fromRedis = redisTemplate.opsForValue().get("hello");
        System.out.println("fromRedis = " + fromRedis);
    }
}
