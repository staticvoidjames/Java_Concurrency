package Thread_Basic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 调用thread.notify()/notifyAll()方法前,必须先获得对象的监视器,不然抛出IllegalMonitorStateException,
 * 也就是说,thread.notify()/notifyAll()必须配合synchronized关键字使用
 * notify()将等待池中的一个线程从等待池中移动移到锁池中,
 * 而notifyAll()则是将等待池中所有的线程全部移到锁池中,被移动的线程由WAITING变为BLOCKED
 * 通知时不会释放lock的锁,synchronized语句块运行完时才会释放
 * wait():释放锁,线程状态由RUNNING变为WAITING,并将当前线程放置到对象的等待队列
 */
public class WaitNotifyDemo {

    static volatile boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();

        TimeUnit.SECONDS.sleep(1);

        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();

        TimeUnit.SECONDS.sleep(1);

        waitThread.interrupt();
    }

    static class Wait implements Runnable {

        public void run() {
            // 加锁,拥有lock的Monitor
            synchronized (lock) {
                // 当条件不满足时,继续wait,同时释放了lock的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true. wait @ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        //调用wait()后,线程状态由RUNNING变为WAITING,并将当前线程放置到对象的等待池中
                        lock.wait();
                        // wait()过程中被打断,如果这个时候锁被其他线程持有,没有办法抛出异常,而在再次获得锁的时候,
                        // wait()方法已经返回了,也就不会抛出异常,线程打断标志位也没有清楚

                        // 如果这里还有一个wait(),那么将会抛出异常
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 条件满足时，完成工作
                System.out.println(Thread.currentThread() + " flag is false. running @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }

    }

    static class Notify implements Runnable {

        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁，然后进行通知，通知时不会释放lock的锁!!!
                // 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + " hold lock. notify @ " +
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                flag = false;
                lock.notify();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep @ "
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

/*
results:  run and see
Thread[WaitThread,5,main] flag is true. wait @ 21:54:25
Thread[NotifyThread,5,main] hold lock. notify @ 21:54:26
Thread[NotifyThread,5,main] hold lock again. sleep @ 21:54:31
Thread[WaitThread,5,main] flag is false. running @ 21:54:36
java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at java.lang.Object.wait(Object.java:502)
	at Thread_Basic.WaitNotify$Wait.run(WaitNotify.java:50)
	at java.lang.Thread.run(Thread.java:748)

 */
