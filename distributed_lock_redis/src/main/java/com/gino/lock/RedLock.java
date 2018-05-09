package com.gino.lock;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * redlock
 * <p>
 * 用 redis db 来模拟 redis node
 * Simulate the redis node with redis db
 *
 * @author gino
 * Created on 2018/5/7
 */
@Slf4j
public class RedLock extends RedisConfig {
    private final AtomicInteger passNode = new AtomicInteger();
    private int nodes;

    public RedLock(int nodes) {
        this.nodes = nodes;
        if (nodes % 2 != 1) {
            log.info("Number of nodes should be odd");
        }
    }

    /**
     * get lock of one node
     *
     * @param lockName
     * @param uuid
     * @param db         as node
     * @param tryTimeout
     * @param keyExpire
     * @return
     */
    private boolean getNodeLock(String lockName, String uuid, int db, long tryTimeout, long keyExpire) {
        Jedis resource = jedisPool.getResource();
        String lockKey = PREFIX + lockName;
        resource.select(db);

        long tryEnd = System.currentTimeMillis() + tryTimeout;
        while (System.currentTimeMillis() < tryEnd) {
            String res = resource.set(lockKey, uuid, "NX", "PX", keyExpire);
            if ("OK".equals(res)) {
                log.info("DB {} Get lock, uuid: {}", db, uuid);
                resource.close();
                return true;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * release lock of one node
     *
     * @param lockName
     * @param uuid
     * @param db       as node
     * @return
     */
    private boolean releaseNodeLock(String lockName, String uuid, int db) {
        Jedis resource = jedisPool.getResource();
        String lockKey = PREFIX + lockName;
        boolean retFlag = false;
        resource.select(db);

        // use lua script for atomicity. The effect is same as Watch Method and Multi Transaction
        try {
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                    "then " +
                    "    return redis.call('del',KEYS[1]) " +
                    "else " +
                    "    return 0 " +
                    "end";
            Object eval = resource.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uuid));
            retFlag = "1".equals(eval.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resource.close();
        }

        return retFlag;
    }


    /**
     * get lock
     *
     * @param lockName
     * @param tryTimeout
     * @param keyExpire
     * @return
     */
    public String getLock(String lockName, long tryTimeout, long keyExpire) {
        // check parameter
        if (tryTimeout > keyExpire || keyExpire < nodes * 50) {
            log.info("Unreasonable time parameters may not be available for lock.");
        }

        // creat thread pool
        ExecutorService executorService = newCachedThreadPool();
        String uuid = UUID.randomUUID().toString();
        passNode.set(0);

        // get start time
        long start = System.currentTimeMillis();

        // try to get lock
        for (int i = 0; i < nodes; i++) {
            final int tmp = i;
            executorService.execute(() -> {
                boolean res = getNodeLock(lockName, uuid, tmp, tryTimeout, keyExpire);
                if (res) {
                    // add pass node value;
                    passNode.incrementAndGet();
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        // get end time
        long end = System.currentTimeMillis();

        if ((passNode.get() >= nodes / 2 + 1) && (end - start) < keyExpire) {
            log.info("Get RedLock uuid {}, pass node:{}", uuid, passNode.get());
            return uuid;
        } else {
            log.info("Get RedLock failed");
            releaseLock(lockName, uuid);
            return null;
        }
    }

    /**
     * release lock
     *
     * @param lockName
     * @param uuid
     */
    public void releaseLock(String lockName, String uuid) {
        ExecutorService executorService = newCachedThreadPool();
        for (int i = 0; i < nodes; i++) {
            final int tmp = i;
            executorService.execute(() -> releaseNodeLock(lockName, uuid, tmp));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
    }
}
