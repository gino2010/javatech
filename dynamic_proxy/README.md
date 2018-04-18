# Dynamic Proxy

This project is for dynamic proxy

这个工程主要实现三种动态代理：JDK Proxy、 cglib Proxy 和 Javassist Proxy

This project mainly implements three dynamic proxy: JDK Proxy cglib Proxy and Javassist Proxy

### Comparison of three
- JDK Proxy 需要 interface， cglib和javassist不需要，并支持interface方式，这是最主要的区别，也对使用场景做了限制
- JDK Proxy 使用 Java内部反射机制实现，cglib 底层使用asm实现（包依赖中可以看到），javassist自身实现
- JDK Proxy 代理创建速度较快，但函数调用性能不如cglib和javassist
- JDK Proxy 和 cglib 字节码较大，javassist字节码较小

- JDK Proxy requires interface, cglib and javassit do not and support interface mode. This is main difference that restrict usage scenarios.
- JDK Proxy user the java internal reflection mechanism, cglib uses the asm implementation(you can find it in dependencies), javassist implements itself.
- JDK Proxy is faster to create proxy, but the performance of call function is inferior to cglib and javassist.
- JDK Proxy and cglib have larger bytecode size and javassist bytecode size is smaller.
