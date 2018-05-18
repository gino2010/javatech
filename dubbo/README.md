# Dubbo Demo
目前，我们说到分布式服务之间主流的通讯方式，除了MQ就是RPC。说到RPC就不得不提大名鼎鼎的[Dubbo](http://dubbo.incubator.apache.org/)。
一度时间阿里不在维护Dubbo，最近将其贡献给apache后又开始维护更新。听说很多大公司也在使用它，
或者基于他进行了深度定制开发。虽然我所在团队平时主要使用的Spring Cloud来构建微服务，
但无论怎样，这个东西还是需要知道的。毕竟分布式服务系统场景复杂，每个技术都有自己特点。

Now, we talk about the popular communication way among distributed services, except MQ is RPC. 
Speaking of RPC, we have to mention the famous [Dubbo](http://dubbo.incubator.apache.org/). For a time, 
Ali did not maintain Dubbo any more. He recently contributed it to apache and began to maintain again.
I heard that many companies are using it, or custom development based on it. Although my team usually uses
Spring Cloud to build microservices, but no matter what, it still needs to be known. After all, distributed service 
systems have complex scenarios. Each technology has its own characteristics.

### Environment
- 在 Ubuntu Server 上，docker 启动 [zookeeper](https://hub.docker.com/_/zookeeper/)
- 在 Ubuntu Server 上，启动 tomcat9.0.7 运行 [dubbo-admin](https://github.com/apache/incubator-dubbo-ops/tree/master/dubbo-admin)
- 在 Mac OS X 上，运行生产者和消费者服务 [office code](https://github.com/apache/incubator-dubbo/tree/master/dubbo-demo)

- On Ubuntu Server, docker run [zookeeper](https://hub.docker.com/_/zookeeper/)
- On Ubuntu Server, start tomcat9.0.7 and deploy [dubbo-admin](https://github.com/apache/incubator-dubbo-ops/tree/master/dubbo-admin)
- On Mac OS X, run provider and consumer service [office code](https://github.com/apache/incubator-dubbo/tree/master/dubbo-demo)

### Summary
2018-05-18

参考官方示例代码，初步体验dubbo。
- 由于对Spring boot的支持未到2.0版本所以先放弃尝试了。
- 普通版本由于maven仓库尚未即时更新，此次代码尝试版本为dubbo 2.6.1，dubbo 2.6.2将xml 的 schema切换到了apache下所以需要进行调整
- multicast本机模式时，需要关闭网卡，指定ipv4，否则：dubbo multicast Can't assign requested address

结论，初步体验还是很简单的。后续我会尝试其它rpc实现方式，例如grpc。这个在孵化的项目目前变化比较频繁，如果拿来直接使用还是要更多考虑其成熟度。

Referring to the official sample code, I initially experienced Dubbo。

- Because support for Spring boot 2.0 has not release, so I give up to try using spring boot
- Because maven warehouse has not been updated immediately, this project use dubbo 2.6.1. In dubbo 2.6.2, xml schema change to apache domain, so you need to adjust.
- Under multicast mode, you need to close wifi and set ipv4 prefer, otherwise: dubbo multicast Can't assign requested address

In conclusion, the initial experience is still very simple. Afterward I will try other RPC implementation, such as gRPC.
This incubation project is currently changing more frequently. If you want to use it directly, you must consider its mature more. 