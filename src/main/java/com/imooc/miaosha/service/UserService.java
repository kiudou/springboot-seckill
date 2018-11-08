package com.imooc.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserDao userDao;

    @Resource
    JedisPool jedisPool;

    @Resource
    RedisConfig redisConfig;

    public User getById(int id){
        return userDao.getById(id);
    }


    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str =  jedis.get(key);
            T t = stringToBean(str);
            return t;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }

    }


    public <T> boolean set(String key, T value) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null) {
                return false;
            }
            jedis.set(key, str);
            return true;
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Bean   //通过这种方式将JedisPool注入到Spring容器中
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait()*1000);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout()*1000,redisConfig.getPassword(),0);
        return jedisPool;
    }


    private <T> T stringToBean(String str) {

        return null;
    }


    private <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String)value;
        }else{
            return JSON.toJSONString(value);
        }

    }


}
