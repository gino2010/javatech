# Thread Safe

This project focus on java thread safe.

这里我们关注这么几个问题：原子性和可见性，同步和锁等问题

Here we are concerned with a few issues: atomicity and volatile, synchronization and lock issues

简单说：多个线程同时访问修改相同的资源，造成了读写错误，我们就认为其线程不安全，反之安全。具体场景有很多。

Simplistically: multiple threads access the same resource at the same time, causing read and write errors. 
So we consider that the thread is not safe, and vice versa. There are many specific scenarios.

- 模拟多线程不安全情况
- 模拟多线程安全情况

- simulate multi-thread not safe
- simulate multi-thread safe