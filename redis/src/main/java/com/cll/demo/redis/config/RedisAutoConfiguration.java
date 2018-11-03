package com.cll.demo.redis.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chenliangliang
 * @date 2018/11/2
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@ComponentScan(basePackages = {"com.cll.demo.redis"})
public class RedisAutoConfiguration {

    private final RedisProperties properties;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool pool = properties.getPool();
        config.setMaxIdle(pool.getMaxIdle());
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        config.setMinIdle(pool.getMinIdle());

        return new JedisPool(config,
                properties.getHost(),
                properties.getPort(),
                properties.getTimeout(),
                properties.getPassword(),
                properties.getDatabase(),
                "jedis-client",
                properties.isSsl());

    }


    RedisAutoConfiguration(RedisProperties properties) {
        this.properties = properties;
    }
}
