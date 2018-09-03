package com.gino.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gino
 * Created on 2018/9/3
 */
@Slf4j
@Component
@ConfigurationProperties("server")
public class ServerProperties {
    private String email;
    private List<Cluster> cluster = new ArrayList<>();

    public static class Cluster {
        private String ip;
        private String path;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "Cluster{" +
                    "ip='" + ip + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cluster> getCluster() {
        return cluster;
    }

    public void setCluster(List<Cluster> cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return "ServerProperties{" +
                "email='" + email + '\'' +
                ", cluster=" + cluster +
                '}';
    }

    @PostConstruct
    public void postConstruct() {
        log.info(this.toString());
    }
}
