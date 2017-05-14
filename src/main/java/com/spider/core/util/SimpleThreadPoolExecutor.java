package com.spider.core.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

//简单的线程执行者类
public class SimpleThreadPoolExecutor{
	private ThreadPoolExecutor executor;
	public SimpleThreadPoolExecutor(){
		executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}
	public SimpleThreadPoolExecutor(ThreadFactory threadFactory){
		executor = (ThreadPoolExecutor)Executors.newCachedThreadPool(threadFactory);
	}
	public SimpleThreadPoolExecutor(int poolsize){
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolsize);
	}
	public SimpleThreadPoolExecutor(int poolsize,ThreadFactory threadFactory){
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolsize, threadFactory);
	}
	public ThreadPoolExecutor getExecutor(){
		return executor;
	}
}
