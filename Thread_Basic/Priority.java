package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * 操作系统可能忽略优先级的设置
 * 推荐优先级设置为Thread.MIN_PRIORITY、Thread.NORM_PRIORITY和Thread.MAX_PRIORITY
 * 程序正确性不能依赖线程的优先级高低
 * 运行环境:Windows 7 64bit的时候,优先级大的比优先级低的运行次数差30-100000倍(CPU占用100%....)
 * macOS 10.12.6:几乎没有区别
 * 运行环境为MacOS的时候几乎没有区别
 */
public class Priority{
	
	private static volatile boolean notStart = true;
	private static volatile boolean notEnd = true;
	
	public static void main(String[] args) throws InterruptedException{
		Job[] jobs = new Job[10];
		for(int i = 0;i < 10;i++){
			int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
			jobs[i] = new Job(priority);
			Thread thread = new Thread(jobs[i],"Thread:" + i);
			thread.setPriority(priority);
			thread.start();
		}
		notStart = false;
		// 当前
		TimeUnit.SECONDS.sleep(5);
		notEnd = false;
		for(Job job : jobs){
			System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount);
		}
	}
	
	static class Job implements Runnable{
		
		private int priority;
		private long jobCount;
		
		public Job(int priority){
			this.priority = priority;
		}
		
		public void run(){
			while(notStart){
				Thread.yield();
			}
			while(notEnd){
				jobCount++;
			}
		}
	}
	
}
/*
result(Windows 7 64bit):
Job Priority : 1, Count : 28997038
Job Priority : 1, Count : 36907
Job Priority : 1, Count : 18435343
Job Priority : 1, Count : 1787345186
Job Priority : 1, Count : 304180667
Job Priority : 10, Count : 8875288254
Job Priority : 10, Count : 9004718411
Job Priority : 10, Count : 7586617842
Job Priority : 10, Count : 7607083492
Job Priority : 10, Count : 1618341030


result(macOS 10.12.6):
Job Priority : 1, Count : 554944414
Job Priority : 1, Count : 549827876
Job Priority : 1, Count : 547251852
Job Priority : 1, Count : 543255503
Job Priority : 1, Count : 552223954
Job Priority : 10, Count : 546890343
Job Priority : 10, Count : 549027435
Job Priority : 10, Count : 547720331
Job Priority : 10, Count : 546289658
Job Priority : 10, Count : 546834005
 */
