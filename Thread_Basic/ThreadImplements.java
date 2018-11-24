package Thread_Basic;

/**
 * 线程两种实现方式:
 * 1. 继承Thread类,重写run()
 * 2. 实现Runnable接口,重写run(),提交该Runnable到Thread中
 * 其实这两种实现方式,是一样的,因为Thread类本身就实现了Runnable接口
 * 第一种方式并不实用,因为一旦继承了Thread类,就没有办法继承其他类了,而接口是可以多实现的
 */
public class ThreadImplements{
	
	public static void main(String[] args){
		Thread thread = new ExtendThread();
		thread.start();
		
		RunnableImpl runnable = new RunnableImpl();
		thread = new Thread(runnable);
		thread.start();
    }
	
}

class ExtendThread extends Thread{
	@Override
	public void run(){
		System.out.println("extends Thread");
	}
}

// Demonstration of the Runnable interface.
class RunnableImpl implements Runnable{
	
	public void run(){
		System.out.println("implements Runnable");
	}
	
}
/*
results:
extends Thread
implements Runnable
 */