package com.gino.lock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * Redis单节点情况下的分布式锁实现
 * Implementation of distributed lock base on single redis instance
 *
 * @author gino
 * Created on 2018/5/2
 */
@Slf4j
public class SingleRedisLock {
    private static final String CONFIG = "config.properties";
    private static final String PREFIX = "dislock:";
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 6379;
    private static final int TIMEOUT = 2000;

    private static JedisPool jedisPool;

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

    public static String getLock(String lockName, long tryTimeout, long keyExpire) {
        Jedis resource = jedisPool.getResource();
        String uuid = UUID.randomUUID().toString();
        String lockKey = PREFIX + lockName;

        long tryEnd = System.currentTimeMillis() + tryTimeout;
        while (System.currentTimeMillis() < tryEnd) {
            String res = resource.set(lockKey, uuid, "NX", "PX", keyExpire);
            if ("OK".equals(res)) {
                resource.close();
                log.info("Get lock, uuid: {}", uuid);
                return uuid;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("Get lock, failed!");
        return null;
    }

    public static boolean releaseLock(String lockName, String uuid) {
        Jedis resource = jedisPool.getResource();
        String lockKey = PREFIX + lockName;
        boolean retFlag = false;

        resource.watch(lockKey);
        if (uuid.equals(resource.get(lockKey))) {
            Transaction multi = resource.multi();
            Response<Long> del = multi.del(lockKey);
            multi.exec();
            if (del.get() == 1) {
                log.info("Release lock, uuid: {}", uuid);
                retFlag = true;
            } else {
                log.info("Release lock, failed!");
            }
        }
        resource.unwatch();
        resource.close();
        return retFlag;
    }
}
