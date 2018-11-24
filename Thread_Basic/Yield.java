package Thread_Basic;

public class Yield extends Thread{
	
	public static void main(String[] args){
		
		Yield demo = new Yield();
		demo.start();
		
		for(int i = 0;i < 1000;i++){
			if(i % 20 == 0){
				/**
				 * 向虚拟机表示让出CPU时间片的意图
				 * 但是CPU有权利忽略这个意图
				 * 静态方法
				 */
				Thread.yield();
			}
			System.out.println("main...." + i);
		}
	}
	
	@Override
	public void run(){
		for(int i = 0;i < 1000;i++){
			System.out.println("yield...." + i);
		}
	}
	
}
/*
result: run and see
 */