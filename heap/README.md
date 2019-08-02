# JVM HEAP Test
This project is for heap test work

Reference: [Which memory is faster Heap or ByteBuffer or Direct](http://ashkrit.blogspot.com/2013/07/which-memory-is-faster-heap-or.html)

Fork that github project code

## How to run
```shell script
java -Xms4g -Xmx4g TestMemoryAllocator HEAP 50000000
java -Xms4g -Xmx4g TestMemoryAllocator BB 50000000
java -Xms4g -Xmx4g TestMemoryAllocator DBB 50000000
java -Xms4g -Xmx4g TestMemoryAllocator OFFHEAP 50000000
```

## One more question
The underlying implementation of ByteBuffer uses the Unsafe class. What is the difference between it and OffHeap?