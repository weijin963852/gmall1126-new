package com.atguigu.gmall.item;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
@Slf4j
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
