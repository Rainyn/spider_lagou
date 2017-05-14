package com.spider.core.util;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolMonitor implements Runnable {

	private ThreadPoolExecutor executor;
    public static volatile boolean isStopMonitor = false;
    private String name = "";
    public ThreadPoolMonitor(ThreadPoolExecutor executor,String name){
        this.executor = executor;
        this.name = name;
    }
	@Override
	public void run() {
		while(!isStopMonitor){
			//System.out.printf("Server: Pool Size: %d\n",executor.getPoolSize());
			//System.out.printf("Server: Active Count: %d\n",executor.getActiveCount());
			//System.out.printf("Server: Completed Tasks: %d\n",executor.getCompletedTaskCount());
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("InterruptedException");
			}
		}
		
	}
	
}
