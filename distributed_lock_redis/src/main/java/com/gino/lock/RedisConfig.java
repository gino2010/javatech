package com.gino.lock;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * redis 配置信息
 *
 * @author gino
 * Created on 2018/5/7
 */
public class RedisConfig {
    private static final String CONFIG = "config.properties";
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 6379;
    private static final int TIMEOUT = 2000;

    protected static final String PREFIX = "dislock:";
    protected static JedisPool jedisPool;

    // initial configuration from properties file
    static {
        ClassLoader classLoader = SingleRedisLock.class.getClassLoader();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(100_000);

        InputStream inputStream = classLoader.getResourceAsStream(CONFIG);
        if (inputStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jedisPool = new JedisPool(config, properties.getProperty("address", ADDRESS),
                    Integer.parseInt(properties.getProperty("port", String.valueOf(PORT))),
                    Integer.parseInt(properties.getProperty("timeout", String.valueOf(TIMEOUT))));
        } else {
            jedisPool = new JedisPool(config, ADDRESS, PORT, TIMEOUT);
        }
    }
}
