package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * 当JVM中不存在非Daemon线程时jvm就会退出
 * 在JVM退出时,并不保证Daemon线程中finally块一定会执行
 * 所以,在构建Daemon线程时,不能依靠finally块中的内容来确保执行关闭或清理资源的逻辑
 * Daemon threads spawn other daemon threads
 * daemon线程派生出的子线程,默认为daemon线程
 */
public class Daemon{

	public static void main(String[] args){
		Thread thread = new Thread(new DaemonRunner());
		thread.setDaemon(true);
		thread.start();
	}

	static class DaemonRunner implements Runnable{
		@Override
		public void run(){
			try{
				try{
					TimeUnit.SECONDS.sleep(100);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}finally{
				System.out.println("DaemonThread finally run.");
			}
		}
	}

}
