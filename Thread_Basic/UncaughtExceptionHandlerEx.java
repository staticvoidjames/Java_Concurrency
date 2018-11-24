package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * 捕获线程中未被捕获的异常(UncaughtException);
 * 这种异常的处理逻辑:
 * 1. 如果线程本身存在自身的UncaughtExceptionHandler,给该UncaughtExceptionHandler处理;否则,交给所在线程组处理;
 * 2. 线程组处理的方式为:
 * 2.1 如果线程组不是根线程组,交给父线程组处理,直到交给根线程组处理;
 * 2.2 查看是否有Thread默认的UncaughtExceptionHandler,如果有,则交给该UncaughtExceptionHandler处理,如果没有默认的UncaughtExceptionHandler,则线程组自己处理;
 * ThreadGroup处理这种异常的方式,参考源码{@link ThreadGroup#uncaughtException(Thread,Throwable)}
 */
public class UncaughtExceptionHandlerEx{
	
	public static void main(String[] args) throws InterruptedException{
		
		System.out.println("thread has no its own UncaughtExceptionHandler,no default UncaughtExceptionHandler,thread group deal this");
		Thread thread = new Thread(new ExceptionRunnable());
		thread.start();
		TimeUnit.SECONDS.sleep(1);
		
		thread = new Thread(new ExceptionRunnable());
		// 设置了Thread类默认的UncaughtExceptionHandler,
		// 线程退出时的未捕获异常会在这里被捕获,并调用UncaughtExceptionHandler的uncaughtException()方法
		// 通过这个静态方法设置的默认的UncaughtExceptionHandler,对所有线程均有效
		System.out.println("thread has no its own UncaughtExceptionHandler,Thread has default UncaughtExceptionHandler to deal this");
		Thread.setDefaultUncaughtExceptionHandler((t,e) -> {
			System.out.println("DefaultUncaughtExceptionHandler caught this");
			e.printStackTrace();
		});
		thread.start();
		TimeUnit.SECONDS.sleep(1);
		
		System.out.println("thread has its own UncaughtExceptionHandler to deal this");
		thread = new Thread(new ExceptionRunnable());
		//设置线程独有的UncaughtExceptionHandler
		thread.setUncaughtExceptionHandler((t,e) -> {
			System.out.println("I caught this myself");
			e.printStackTrace();
		});
		thread.start();
	}
	
}

class ExceptionRunnable implements Runnable{
	
	// 如果没有设置UncaughtExceptionHandler,这里抛出异常,没有捕获,可能导致一些资源没有被正确关闭
	public void run(){
		throw new RuntimeException("this is a uncaught exception");
	}
	
}
/*
results:
thread has no its own UncaughtExceptionHandler,no default UncaughtExceptionHandler,thread group deal this
Exception in thread "Thread-0" java.lang.RuntimeException: this is a uncaught exception
	at Thread_Basic.ExceptionRunnable.run(UncaughtExceptionHandlerEx.java:51)
	at java.lang.Thread.run(Thread.java:748)
thread has no its own UncaughtExceptionHandler,Thread has default UncaughtExceptionHandler to deal this
DefaultUncaughtExceptionHandler caught this
java.lang.RuntimeException: this is a uncaught exception
	at Thread_Basic.ExceptionRunnable.run(UncaughtExceptionHandlerEx.java:51)
	at java.lang.Thread.run(Thread.java:748)
thread has its own UncaughtExceptionHandler to deal this
I caught this myself
java.lang.RuntimeException: this is a uncaught exception
	at Thread_Basic.ExceptionRunnable.run(UncaughtExceptionHandlerEx.java:51)
	at java.lang.Thread.run(Thread.java:748)
 */