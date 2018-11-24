package Thread_Basic;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 查看当前运行线程信息
 */
public class CurrentThreads{
	
	public static void main(String[] args){
		// 用堆栈信息显示
		Map<Thread,StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
		// 遍历线程信息，仅打印线程ID和线程名称信息
		Set<Map.Entry<Thread,StackTraceElement[]>> entries = allStackTraces.entrySet();
		Iterator<Map.Entry<Thread,StackTraceElement[]>> iterator = entries.iterator();
		while(iterator.hasNext()){
			System.out.println("---------------------");
			Map.Entry<Thread,StackTraceElement[]> next = iterator.next();
			Thread key = next.getKey();
			System.out.println(key);
			StackTraceElement[] value = next.getValue();
			for(int i = 0;i < value.length;i ++){
				System.out.println(value[i]);
			}
		}
		// 获取Java线程管理MXBean
		//ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 不需要获取同步的monitor和synchronizer信息，仅仅获取线程和线程堆栈信息
		//ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
		//for(ThreadInfo threadInfo : threadInfos){
		//	System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
		//}
		//[4]Signal Dispatcher:分发处理发送给JVM信号的线程
		//[3]Finalizer:调用对象finalize方法的线程
		//[2] Reference Handler:清除Reference的线程
		//[1]main:main线程,用户程序入口
	}
	
}

/*
results:
Thread[Monitor Ctrl-Break,5,main]
java.util.ArrayList.iterator(ArrayList.java:840)
java.net.SocksSocketImpl.connect(SocksSocketImpl.java:384)
java.net.Socket.connect(Socket.java:589)
java.net.Socket.connect(Socket.java:538)
java.net.Socket.<init>(Socket.java:434)
java.net.Socket.<init>(Socket.java:211)
com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:59)
---------------------
Thread[Attach Listener,5,system]
---------------------
Thread[main,5,main]
java.lang.Thread.dumpThreads(Native Method)
java.lang.Thread.getAllStackTraces(Thread.java:1610)
Thread_Basic.CurrentThreads.main(CurrentThreads.java:17)
---------------------
Thread[Signal Dispatcher,9,system]
---------------------
Thread[Finalizer,8,system]
java.lang.Object.wait(Native Method)
java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
---------------------
Thread[Reference Handler,10,system]
java.lang.Object.wait(Native Method)
java.lang.Object.wait(Object.java:502)
java.lang.ref.Reference.tryHandlePending(Reference.java:191)
java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
 */