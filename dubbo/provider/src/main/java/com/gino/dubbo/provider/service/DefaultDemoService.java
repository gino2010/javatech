package com.gino.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.gino.dubbo.api.DemoService;

/**
 * @author gino
 * Created on 2018/5/13
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
