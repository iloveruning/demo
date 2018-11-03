package com.cll.demo.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @author chenliangliang
 * @date 2018/11/2
 */
@Slf4j
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long UNLOCK_SUCCESS = 1L;
    private static final String LUA_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

    private JedisPool jedisPool;

    private RedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static RedisLock getInstance() {
        return new RedisLock(SpringContextHolder.getBean(JedisPool.class));
    }


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超时时间
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = getJedis();
        try {
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            return LOCK_SUCCESS.equals(result);
        } finally {
            close(jedis);
        }
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param expireTime 超时时间
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, int expireTime) {
        return tryLock(lockKey, String.valueOf(Thread.currentThread().getId()), expireTime);
    }


    /**
     * 阻塞获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超时时间
     * @param timeout    阻塞时间
     * @return 是否获取成功
     */
    public boolean lock(String lockKey, String requestId, int expireTime, int timeout) {
        long endTime = System.currentTimeMillis() + timeout;
        while (true) {
            if (tryLock(lockKey, requestId, expireTime)) {
                return true;
            }
            if (System.currentTimeMillis() >= endTime) {
                return false;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                //ignore
            }
        }
    }


    public boolean lock(String lockKey, int expireTime, int timeout) {
        return lock(lockKey, String.valueOf(Thread.currentThread().getId()), expireTime, timeout);
    }


    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean unLock(String lockKey, String requestId) {
        Jedis jedis = getJedis();
        try {
            Object result = jedis.eval(LUA_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return UNLOCK_SUCCESS.equals(result);
        } finally {
            close(jedis);
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey 锁
     * @return 是否释放成功
     */
    public boolean unLock(String lockKey) {
        return unLock(lockKey, String.valueOf(Thread.currentThread().getId()));
    }


    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
