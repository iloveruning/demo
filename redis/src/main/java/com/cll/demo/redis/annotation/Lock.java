package com.cll.demo.redis.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/11/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lock {

    String lockPrefix() default "";

    String lockKey();

    int expireTime();

    int timeout() default -1;

}
