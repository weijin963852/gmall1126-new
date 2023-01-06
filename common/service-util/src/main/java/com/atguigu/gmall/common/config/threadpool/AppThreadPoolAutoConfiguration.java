package com.atguigu.gmall.common.config.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(ThreadPoolPoperties.class)
public class AppThreadPoolAutoConfiguration {

    @Autowired
    ThreadPoolPoperties threadPoolPoperties;

    @Value("${spring.application.name}")
    String threadName;
    int i = 0;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                threadPoolPoperties.getCorePoolSize(),
                threadPoolPoperties.getMaxPoolSize(),
                threadPoolPoperties.getKeepAliveTime(),
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(threadPoolPoperties.getCapacity()),
                r -> {

                    Thread thread = new Thread(r);
                    thread.setName(threadName + "[core-thread-" + i++ + "]");
                    thread.setPriority(1);//线程池优先级
                    return thread;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }
}
