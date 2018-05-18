package com.gino.dubbo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.gino.dubbo.api.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * demo servie implemetation
 *
 * @author gino
 * Created on 2018/5/18
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
