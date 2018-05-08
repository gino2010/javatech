# Distributed Locks with Redis


分布式锁的实现方式主要有三种：

- 数据库方式，基于主键唯一性约束实现或行级锁实现
- Redis方式，基于setNX操作实现
- Zookeeper实现

There are three main ways to implement distributed locks:

- Database way, implementation base on the uniqueness of primary key or row-level locks
- Redis way, implementation base on sexNX command
- Zookeeper way

初步了解，Redis方式是现在大家比较推荐的一种方式，Zookeeper应该是最严谨的一种方式。
因此，此工程选择Redis方式，作为练习分布式锁的开始

I preliminary learned that Redis way is a more recommended way now. Zookeeper is the most rigorous way.
So, this project chooses Redis way as the beginning of practicing distributed locks.

### Why use it

简单的说，分布式锁用于多处理过程竞争共享资源时使用，从而保证在一个时刻只有一个处理过程在操作共享资源，避免冲突。
目的类似于线程锁，但它是用于协调多个服务（在不同服务器上的进程）。

Simply put, distributed locks are used when multiple processes compete for shared resource, thus ensuring that only 
one process operates shared resource at a time, avoiding conflicts. The purpose is similar to thread lock, but it is 
used to coordinate multiple services(processes on different servers)

### How to implement

这是一个很有意思的话题，随着阅读网上的文章，我发现其实现方式逐步复杂，也有很多人们尚未充分理解或争议的地方

This is very interesting topic. As I read some articles on the internet, I find that its implementations 
are gradually becoming more complicated and there are many parts that people have not yet fully understand or disputed.

建议阅读：[Redlock](https://redis.io/topics/distlock)，这里不仅有详细的实现说明，还有实现代码。

Reference: [Redlock](https://redis.io/topics/distlock), Not only detailed implementation instructions, but also implementation code.

当然，我也会根据理解书写自己的实现版本作为练习。

Of course, I also write my own implementation version as an exercise.

### My Implementation

首先实现一个redis实例即单节点的例子，这个是基础。通过多线程方式模拟测试，实际应用不应该是多线程环境，否则使用线程同步相关技术就好了。

The first implement example is base on a redis instance that is a single node, this is foundation. 
Test code uses multi-threaded simulation, the actual application environment should not be a multi-threaded, 
otherwise it would be better to use the thread synchronization related technology.

初步实现了RedLock方式，用于多个Redis节点，增强其稳定性。但实际使用可能还是单节点比较多，需要考虑锁失效的弥补方式。

The RedLock way was initially implemented for multiple Redis nodes to enhance their stability. 
However, the actual use may be more single-node, and it is necessary to consider the way to make up the lock failure. 

### Defect

- 没有使用lua脚本对redis进行操作
- RedLock 没有实现多节点，只是通过不同db进行多节点实现模拟

- Do not use lua script to manipulate redis
- RedLock does not implement multi-nodes, just simulate multi-node with different db