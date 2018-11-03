package com.cll.demo.example;

import com.cll.demo.redis.RedisLock;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
public class RedisToolTest extends BaseMvcTest {



    @Test
    public void jedisPool_test(){
        JedisPool jedisPool = context.getBean(JedisPool.class);
        Assert.assertNotNull(jedisPool);
    }


    @Test
    public void redisLock_test(){
        RedisLock lock = RedisLock.getInstance();

        if (lock.tryLock("lock_key",5000)){
            System.out.println("获取锁成功");
            //lock.unLock("lock_key");
        }else {
            System.out.println("获取锁失败");
        }
    }
}
