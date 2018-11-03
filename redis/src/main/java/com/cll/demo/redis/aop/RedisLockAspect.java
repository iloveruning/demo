package com.cll.demo.redis.aop;

import com.cll.demo.redis.RedisLock;
import com.cll.demo.redis.annotation.Lock;
import com.cll.demo.redis.exception.RedisLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    private static final String DEFAULT_LOCK_PREFIX = "lock";


    @Pointcut("@annotation(com.cll.demo.redis.annotation.Lock)")
    public void lockPointcut() {
    }


    @Around("lockPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Lock lockAnnotation = method.getAnnotation(Lock.class);

        String lockKey = lockAnnotation.lockKey();
        if (StringUtils.isEmpty(lockKey)) {
            log.error("lockKey can not be empty!");
            throw new RedisLockException("lockKey can not be empty!");
        }

        int expireTime = lockAnnotation.expireTime();
        if (expireTime <= 0) {
            log.error("expireTime must greater than zero!");
            throw new RedisLockException("expireTime must greater than zero!");
        }

        String lockPrefix = StringUtils.isEmpty(lockAnnotation.lockPrefix()) ? DEFAULT_LOCK_PREFIX : lockAnnotation.lockPrefix();
        String key = lockPrefix + "_" + lockKey;

        int timeout = lockAnnotation.timeout();

        RedisLock lock = RedisLock.getInstance();

        boolean process = timeout > 0 ? lock.lock(key, expireTime, timeout) : lock.tryLock(key, expireTime);
        if (process) {
            try {
                return joinPoint.proceed();
            } finally {
                lock.unLock(key);
            }
        }else {
            log.warn("获取锁失败");
            throw new RedisLockException("fail to get lock!");
        }

    }

}
