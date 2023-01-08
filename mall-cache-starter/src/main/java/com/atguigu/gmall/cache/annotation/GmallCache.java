
package com.atguigu.gmall.cache.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GmallCache {

    String cacheKey() default "";

    String bloomName() default "";

    String bloomValue() default "";

    String lockName() default "";

    //过期时间
    long dataTtl() default 60 * 30l;

}
