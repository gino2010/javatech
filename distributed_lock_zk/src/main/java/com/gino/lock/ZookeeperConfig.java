package com.gino.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gino
 * Created on 2018/10/22
 */
public class ZookeeperConfig {
    private static final String CONFIG = "config.properties";
    private static final String bootserver = "127.0.0.1:2181";
    private static final int sleep = 1000;
    private static final int retry = 3;

    public static String path = "/zklock";
    public static CuratorFramework client;

    static {
        ClassLoader classLoader = ZookeeperConfig.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(CONFIG);
        if (inputStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(Integer.parseInt(properties.getProperty("sleep", String.valueOf(sleep))),
                    Integer.parseInt(properties.getProperty("retry", String.valueOf(retry))));
            client = CuratorFrameworkFactory.newClient(properties.getProperty("bootserver", bootserver), retryPolicy);
            path = properties.getProperty("path", path);
        } else {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(sleep, retry);
            client = CuratorFrameworkFactory.newClient(bootserver, retryPolicy);
        }
    }
}
