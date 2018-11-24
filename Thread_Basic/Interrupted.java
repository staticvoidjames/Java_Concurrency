package Thread_Basic;

import java.util.concurrent.TimeUnit;

/**
 * 在抛出InterruptedException前,Java虚拟机会先将该线程的标志位清除
 * 再调用thread.isInterrupted()时,会返回false
 */
public class Interrupted {

    public static void main(String[] args) throws Exception {
    	
        Thread sleepThread = new SleepThread();
        sleepThread.setDaemon(true);
        sleepThread.start();
        
        Thread busyThread = new BusyThread();
        busyThread.setDaemon(true);
        busyThread.start();

        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        
        // 防止sleepThread和busyThread立刻退出
        TimeUnit.SECONDS.sleep(2);
    }

    static class SleepThread extends Thread {
        @Override
        public void run() {
            while (true) {
	            try{
		            Thread.sleep(10000);
	            }catch(InterruptedException e){
	            	// 收到这个异常的时候,终端标志位已被清除
		            System.out.println("SleepThread interrupted flag is " + isInterrupted());
		            e.printStackTrace();
	            }
            }
        }
    }

    static class BusyThread extends Thread {
        @Override
        public void run() {
            while (true) {}
        }
    }

}
/*
result:
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at Thread_Basic.Interrupted$SleepThread.run(Interrupted.java:35)
BusyThread interrupted is true
SleepThread interrupted flag is false
 */