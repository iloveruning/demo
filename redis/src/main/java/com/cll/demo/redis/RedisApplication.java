package com.cll.demo.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {

        JedisPoolConfig config=new JedisPoolConfig();


        SpringApplication.run(RedisApplication.class, args);
    }
}
