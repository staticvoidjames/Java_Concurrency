package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * thread.join():当前线程等待thread线程终止后才从thread.join()处返回
 * join()的实现方式:通过wait()方式实现,每个线程在终止时,会调用线程自身的notifyAll()方法,
 * 通知所有等待在该线程对象上的线程
 */
public class Join{

	public static void main(String[] args) throws InterruptedException{

		Thread previous = Thread.currentThread();
		
		for(int i = 0;i < 10;i++){
			// 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
			Thread thread = new Thread(new Domino(previous),String.valueOf(i));
			thread.start();
			// start()之后,线程只是进入RUNNABLE状态,这里休眠500ms,等待线程被调度运行
			TimeUnit.MILLISECONDS.sleep(500);
			previous = thread;
		}

		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName() + " terminate.");
	}

	static class Domino implements Runnable{
		private Thread thread;

		public Domino(Thread thread){
			this.thread = thread;
		}

		public void run(){
			try{
				System.out.println(thread + "joined");
				thread.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " terminate.");
		}
	}

}