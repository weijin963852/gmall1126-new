package com.atguigu.gmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
public class ThreadPoolTest {
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Test
    void test(){
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(()->{

                System.out.println(Thread.currentThread().getName() + "  hh");
            });
        }

    }
}
