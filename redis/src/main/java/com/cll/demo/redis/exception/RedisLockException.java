package com.cll.demo.redis.exception;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
public class RedisLockException extends RuntimeException {

    public RedisLockException(String msg){
        super(msg);
    }
}
