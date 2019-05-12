package com.cj.test.thread;

public class ThreadTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * System.out.println(Thread.currentThread().getName()); MyThread myThread = new
		 * MyThread(); myThread.start(); System.out.println("ÔËĞĞ½áÊø£¡");
		 */
		
		try {
			MyThread thread = new MyThread();
			thread.setName("myThread");
			thread.start();
			for (int i = 0; i < 10; i++) {
				int time = (int) (Math.random()*1000);
				Thread.sleep(time);
				System.out.println("main="+Thread.currentThread().getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
