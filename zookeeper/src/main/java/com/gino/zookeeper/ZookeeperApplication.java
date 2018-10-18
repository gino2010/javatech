package com.gino.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
public class ZookeeperApplication implements CommandLineRunner {

    @Value("${zc}")
    private String zc;


    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    DemoProperties demoProperties;

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("zookeeper application server start...");
        curatorFramework.blockUntilConnected();
        Stat stat = curatorFramework.checkExists().forPath("/test");
        if (stat == null) {
            curatorFramework.create().creatingParentsIfNeeded()
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/test", "data".getBytes());
        }
        byte[] bytes = curatorFramework.getData().forPath("/test");
        log.info("path test:{}", new String(bytes));

        // set one value
        log.info("zc from zookeeper configuration:{}", zc);

        // set one object, you can change it after you start server
        log.info("demo properties key: {}", demoProperties.getKey());
        log.info("demo properties value: {}", demoProperties.getValue());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit((Runnable) () -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("demo properties key: {}", demoProperties.getKey());
            }
        });
    }
}
