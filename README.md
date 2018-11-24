# Java_Concurrency

#### 项目介绍
**并发基础**及**java并发**学习代码及笔记,我的一些[博客](https://www.cnblogs.com/jamesvoid/)中并发相关的代码也会保存在此  
暂时学习内容范围为:  
[马士兵老师高并发编程系列](https://www.bilibili.com/video/av11076511?from=search&seid=1018913307030627764)  视频
《Java并发编程的艺术》 方腾飞 魏鹏 程晓明 著，机械工业出版社
《Java编程思想》第4版，[美]Bruce Eckel 著，陈昊鹏 译，机械工业出版社
《Java核心技术卷·卷 I》第10版，[美] 凯.S.霍斯特曼(Cay S. Horstmann)著，周立新 等  译，机械工业出版社
《深入理解Java虚拟机》 第2版，周志明  著，机械工业出版社
《操作系统之哲学原理》第2版,邹恒明 著,机械工业出版社	已绝版
《Effective Java》第2版 [美]Joshua Bolch 著 杨春花  俞黎敏 译  机械工业出版社
上述学习范围内的,部分只会略看一遍便于查漏补缺
部分知识,在GitHub上已经有比较好的解释了,将参考[**Java-concurrency**](https://github.com/CL0610/Java-concurrency)项目中内容
编码:UTF-8, I hate GBK  
运行环境:Oracle jdk8

#### 目录
为了避免因英文命名导致难以找到对应源代码的问题,这里给出目录  

**Thread_Basic 线程基础**

| 文件名                          | 内容                                |
| :------------------------------ | ----------------------------------- |
| ThreadImplements.java           | 线程的两种实现方式                  |
| CurrentThreads.java             | 查看当前运行线程信息                |
| Priority.java                   | 线程优先级                          |
| ThreadGroupTest.java            | 线程组相关                          |
| ThreadState.java                | 线程状态                            |
| Daemon.java                     | 守护线程                            |
| Interrupted.java                | 线程打断                            |
| Join.java                       | Thread.join()                       |
| Yield.java                      | Thread.yield()                      |
| ShutDown.java                   | 优雅地停止线程                      |
| Synchronized.java               | synchronized实现原理                |
| UncaughtExceptionHandlerEx.java | UncaughtExceptionHandler示例        |
| Deprecated.java                 | suspend()/resume()/stop()被遗弃方法 |

