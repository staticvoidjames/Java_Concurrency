package Thread_Basic;

/**
 * 运行javap -v Synchronized.class,得出结果如下:
 * public static void main(java.lang.String[]);
 * flags: ACC_PUBLIC, ACC_STATIC
 * Code:
 * stack=2, locals=3, args_size=1
 * 0: ldc           #2                  // class chapter04_basic_of_concurrency/Synchronized
 * 2: dup
 * 3: astore_1
 * 4: monitorenter    //监视器进入,获取锁
 * 5: aload_1
 * 6: monitorexit    //监视器退出,释放锁
 * 7: goto          15
 * 10: astore_2
 * 11: aload_1
 * 12: monitorexit
 * 13: aload_2
 * 14: athrow
 * 15: invokestatic  #3                  // Method m:()V
 * 18: return
 * <p>
 * public static synchronized void m();
 * flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
 * Code:
 * stack=0, locals=0, args_size=0
 * 0: return
 * 从上面可以看出,同步块的实现使用了monitorenter和monitorexit指令,
 * 同步方法依靠ACC_SYNCHRONIZED来完成
 * 其本质均是对一个对象的监视器(monitor)进行获取,而这个获取过程是排他的,
 * 也就是同一时刻只能有一线程获取到由synchronized所保护的对象的监视器,
 * 任意一个对象都有自己的监视器
 * 任意线程对Object的同步访问,首先要获得Object的监视器,如果获取失败,线程进入同步队列,线程状态变为BLOCKED,
 * 当访问Object的前驱(获得了锁的线程)释放了锁,则该释放操作唤醒阻塞在同步队列中的线程,使其重新尝试对监视器的获取
 */
public class Synchronized{
	
	public static void main(String[] args){
		// 对Synchronized Class对象进行加锁
		synchronized(Synchronized.class){
		}
		// 静态同步方法，对Synchronized Class对象进行加锁
		m();
	}
	
	public static synchronized void m(){
	}
	
}
