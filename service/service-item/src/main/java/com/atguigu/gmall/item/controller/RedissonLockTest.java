package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson")
public class RedissonLockTest {
    @Autowired
    RedissonClient redissonClient;

    /**
     * 分布式普通锁
     * */
    @GetMapping("/common")
    public Result commonLock() throws InterruptedException {

        RLock lock = redissonClient.getLock("common-lock");

        lock.lock();
        System.out.println("获取锁...");
        lock.unlock();
        System.out.println("释放锁...");
        return null;
    }
    /**
     * 分布式读写锁
     * */
    RReadWriteLock writeReadLock;
    @PostConstruct
            public void productReadWriterLock(){

         writeReadLock = redissonClient.getReadWriteLock("writer-lock");
    }
    @GetMapping("/readWriterLock/writer")
    public Result writerValue() throws InterruptedException {
        //获取写锁
        RLock rLock = writeReadLock.writeLock();
        rLock.lock();

        Thread.sleep(20000l);

        rLock.unlock();
        return Result.ok();
    }

    /**
     * 分布式读锁
     * */
    @GetMapping("/readWriterLock/read")
    public Result readValue(){
        RLock readLock = writeReadLock.readLock();
        readLock.lock();

        readLock.unlock();
        return Result.ok(6);
    }

    /**
     * 分布式闭锁
     * */
    @GetMapping("/countDownLock")
    public Result zhaohuan() throws InterruptedException {
        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch("count-down-lock");
        countDownLatch.trySetCount(7l);
        countDownLatch.await();
        System.out.println("神龙出现...");
        return Result.ok();

    }

    @GetMapping("/countDownLock/count")
    public Result countDownLockTest(){
        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch("count-down-lock");

        countDownLatch.countDown();
        return Result.ok();
    }

}
