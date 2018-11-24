package Thread_Basic;

public class ThreadState{
	
	private static Object object = new Object();
	private static int THREAD_SUM = 5;
	private static volatile boolean started;
	
	public static void main(String[] args){
		
		Thread[] th = new Thread[THREAD_SUM];
		for(int i = 0;i < THREAD_SUM;i++){
			th[i] = new StateThread();
		}
		
		System.out.println("after create");
		System.out.println(th[1].getState());
		
		for(int i = 0;i < THREAD_SUM;i++){
			th[i].start();
		}
		
		System.out.println("after start");
		System.out.println(th[1].getState());
		
		started = true;
		// 这里等待1s,等所有的线程都运行完object.wait()
		// 否则如果有些线程运行了object.wait()而有些线程没有运行的话,
		// 运行完成的线程为WAITING状态,而没有运行的为BLOCKED状态(等待获取锁来运行object.wait())
		// TIMED_WAITING,在超时范围内,与此一致
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("after wait");
		for(int i = 0;i < THREAD_SUM;i++){
			System.out.println(th[i].getState());
		}
		
		synchronized(object){
			object.notifyAll();
			System.out.println("after notifyAll");
			// 这里虽然运行了object.notifyAll(),但是
			// 在这里object的监视器锁还没有被释放
			// 其他线程均处于BLOCKED状态
			for(int i = 0;i < THREAD_SUM;i++){
				System.out.println(th[i].getState());
			}
		}
		// 主线程跳出了object同步代码块,所有其他线程均可以竞争锁了,抢到锁的线程运行,
		// 进入RUNNABLE状态,未抢到的依然BLOCKED
		System.out.println("after main thread released object's lock");
		for(int i = 0;i < THREAD_SUM;i++){
			System.out.println(th[i].getState());
		}
	}
	
	static class StateThread extends Thread{
		
		@Override
		public void run(){
			while(!started){}
			synchronized(object){
				try{
					object.wait();
					while(started){}
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
/*
运行结果:
after create
NEW
after start
RUNNABLE
after wait
WAITING
WAITING
WAITING
WAITING
WAITING
after notifyAll
BLOCKED
BLOCKED
BLOCKED
BLOCKED
BLOCKED
after main thread released object's lock
BLOCKED
RUNNABLE
BLOCKED
BLOCKED
BLOCKED
 */
