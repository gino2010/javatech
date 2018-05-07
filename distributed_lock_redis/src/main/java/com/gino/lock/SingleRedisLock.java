package com.gino.lock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * Redis单节点情况下的分布式锁实现
 * Implementation of distributed lock base on single redis instance
 *
 * @author gino
 * Created on 2018/5/2
 */
@Slf4j
public class SingleRedisLock extends RedisConfig {
    /**
     * Get Lock
     *
     * @param lockName   lock name
     * @param tryTimeout try to get lock timeout，if timeout, give up trying
     * @param keyExpire  lock expire time
     * @return get lock return uuid or not get lock return null
     */
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

    /**
     * release lock
     *
     * @param lockName lock name
     * @param uuid     uuid returned when got lock
     * @return true or false
     */
    public static boolean releaseLock(String lockName, String uuid) {
        Jedis resource = jedisPool.getResource();
        String lockKey = PREFIX + lockName;
        boolean retFlag = false;

        try {
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
                multi.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release lock, get error, failed!");
        } finally {
            resource.unwatch();
            resource.close();
        }

        return retFlag;
    }
}
