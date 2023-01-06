package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;
/**
 * 关闭线程池和springboot 监控端口的方法
 * */
@RestController
@Slf4j
public class ThreadPoolMinor {

    /**
     * 关闭线程池
     * */
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/pool/close")
    public Result close(){
        threadPoolExecutor.shutdown();
        return Result.ok();
    }

    @GetMapping("/pool/minor")
    public Result minorThreadPool(){
       //核心线程数
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        //执行任务的数量
        long taskCount = threadPoolExecutor.getTaskCount();
        
        log.info("核心线程数:" + corePoolSize);
        log.info("执行任务的数量:" + taskCount);
        return Result.ok();
    }

}
