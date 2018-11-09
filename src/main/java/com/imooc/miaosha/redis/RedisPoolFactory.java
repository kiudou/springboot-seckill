package com.imooc.miaosha.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Service
public class RedisPoolFactory {

    @Resource
    RedisConfig redisConfig;

    @Bean   //通过这种方式将JedisPool注入到Spring容器中
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait()*1000);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout()*1000,redisConfig.getPassword(),0);
        return jedisPool;
    }
}
