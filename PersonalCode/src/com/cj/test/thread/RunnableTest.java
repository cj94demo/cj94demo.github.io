package com.cj.test.thread;

public class RunnableTest {

	public static void main(String[] args) {
		Runnable runnable = new MyRunnable();
		Thread thread = new Thread(runnable);
		thread.start();
		System.out.println("ÔËĞĞ½áÊø£¡");
	}

}
