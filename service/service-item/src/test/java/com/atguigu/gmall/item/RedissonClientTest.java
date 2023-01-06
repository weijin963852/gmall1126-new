package com.atguigu.gmall.item;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedissonClientTest {
    @Autowired
    RedissonClient redissonClient;

    @Test
    void test(){
        RLock lock = redissonClient.getLock("lock");
        System.out.println("lock = " + lock);
    }
}
