package com.atguigu.gmall.cache.aspect;


import com.atguigu.gmall.cache.annotation.GmallCache;
import com.atguigu.gmall.cache.constant.SysRedisConst;
import com.atguigu.gmall.cache.service.CacheOpsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Aspect
@Component
public class CacheAspect {
/*    @Before("@annotation(com.atguigu.gmall.item.cache.annotation.GmallCache)")
    public void before(){
        System.out.println("前置通知...");
    }*/

    @Autowired
    CacheOpsService cacheOpsService;

    ExpressionParser parser = new SpelExpressionParser();
    ParserContext context = new TemplateParserContext();

    @Around("@annotation(com.atguigu.gmall.cache.annotation.GmallCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
     //1 先查缓存
        String cacheKey = determinCacheKey(joinPoint);

        Type type = getMethodGenericReturnType(joinPoint);


//        SkuDetailsTo cacheData = cacheOpsService.getCacheData(cacheKey, SkuDetailsTo.class);
    Object cacheData = cacheOpsService.getCacheData(cacheKey,type);

        //2 缓存中无数据
        if(cacheData == null){
            //4 问布隆过滤器是否有

            String bloomName = determinBloomName(joinPoint);
            if(!StringUtils.isEmpty(bloomName)){
            Object bloomVal = determinBloomVal(joinPoint);
            boolean contatins = cacheOpsService.bloomContatins(bloomName,bloomVal);
            if(!contatins ){
               //5 布隆说无
               return null;
            }
            }

            //6加分布式锁
            boolean b = false;
            Object data = null;
            String lockName = null;
            try {
                lockName = determinLockName(joinPoint);

            // b = cacheOpsService.tryLock((Long) args[0]);
                b = cacheOpsService.tryLock(lockName);
                if (b) {
                    //7 加锁成功
                    //8 查询数据，准备回源
               data = joinPoint.proceed();

               // 获取注解上的过期时间
                    Long dataTtl = determinTtl(joinPoint);
                    cacheOpsService.saveCache(cacheKey, data,dataTtl);
                    return data;
                } else {
                    Thread.sleep(1000);
                    //return cacheOpsService.getCacheData(cacheKey, SkuDetailsTo.class);
                    return  cacheOpsService.getCacheData(cacheKey,type);
                }
            }finally {
                //9解锁
                if(b){
                    cacheOpsService.unLock(lockName);
               // cacheOpsService.unLock((Long) args[0]);
                }


            }
        }
        //3 缓存中有数据
        return cacheData;
    }

    private Long determinTtl(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        GmallCache methodAnnotation = method.getAnnotation(GmallCache.class);

        long ttl = methodAnnotation.dataTtl();
        return ttl;
    }

    private String determinLockName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        GmallCache methodAnnotation = method.getAnnotation(GmallCache.class);

        String expression = methodAnnotation.lockName();
        if(StringUtils.isEmpty(expression)){
            //锁空，设置默认锁名
            expression = SysRedisConst.DEFAULT_LOCK_NAME + method.getName();
        }
        String lockName = evaluationExpression(expression, joinPoint, String.class);
        return lockName;
    }

    private Object determinBloomVal(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        GmallCache methodAnnotation = method.getAnnotation(GmallCache.class);

        String expression = methodAnnotation.bloomValue();
        Object bloomValue = evaluationExpression(expression, joinPoint, Object.class);
        return bloomValue;
    }

    private String determinBloomName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        GmallCache gmallCacheAnnotation = method.getAnnotation(GmallCache.class);
        String bloomName = gmallCacheAnnotation.bloomName();
        return bloomName;
    }

    private Type getMethodGenericReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        Type type = method.getGenericReturnType();
        return type;
    }

    /**
     * 决定用那个key
     * */
    public String determinCacheKey(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();

        //1 获取方法上面的注解
        GmallCache cacheAnnotation = method.getAnnotation(GmallCache.class);

        //2 获取注解上的属性值
        String expression = cacheAnnotation.cacheKey();

        //3 根据表达式计算cacheKey
        String cacheKey = evaluationExpression(expression,joinPoint,String.class);
        return cacheKey;
    }

    private <T>T evaluationExpression(String expression, ProceedingJoinPoint joinPoint, Class<T> clz) {

        Expression exp = parser.parseExpression(expression,context);

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        Object[] args = joinPoint.getArgs();

        evaluationContext.setVariable("params",args);

        T value = exp.getValue(evaluationContext, clz);
        return value;

    }
}
