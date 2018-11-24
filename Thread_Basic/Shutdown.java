package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * 优雅地终止线程的两种方式:
 * 1. 通过线程中断interrupt(),注意中断标志被清除的情况
 * 2. 通过标志位使线程退出
 * 在运行结果来看,两种方式的终止方式并没有太大差距(在该demo中,第一种略块于第二种)
 * thred.interrupt()方法本身只是将线程的中断标志设置为true,并不会使线程停止,线程也不会因此而死亡
 */
public class Shutdown{
	
	public static void main(String[] args) throws InterruptedException{
		
		Runner one = new Runner();
		Thread countThread = new Thread(one);
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();
		
		TimeUnit.SECONDS.sleep(1);
		
		Runner two = new Runner();
		countThread = new Thread(two);
		countThread.start();
		// 睡眠1秒,main线程对Runner two进行取消,使CountThread能够感知on为false而结束
		TimeUnit.SECONDS.sleep(1);
		two.cancel();
	}
	
	private static class Runner implements Runnable{
		
		private long i;
		// 中断标志应该为volatile的,也就是保证可见性
		private volatile boolean on = true;
		
		@Override
		public void run(){
			while(on && !Thread.currentThread().isInterrupted()){
				i++;
				try{
					Thread.sleep(200);
				}catch(InterruptedException e){
					// 这里会清除中断标志,为了保持中断标志,可以在这个地方,再设置一次
					e.printStackTrace();
					System.out.println(Thread.currentThread().isInterrupted());
					Thread.currentThread().interrupt();
				}
				System.out.println("Count i = " + i);
			}
			System.out.println("Count i = " + i);
		}
		
		public void cancel(){
			on = false;
		}
		
	}
	
}
/*
result:
Count i = 1
Count i = 2
Count i = 3
Count i = 4
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at Thread_Basic.Shutdown$Runner.run(Shutdown.java:43)
	at java.lang.Thread.run(Thread.java:748)
false
Count i = 5
Count i = 5
Count i = 1
Count i = 2
Count i = 3
Count i = 4
Count i = 5
Count i = 5
 */
