package com.spider.lagou.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.spider.lagou.entity.Page;

public abstract class AbstractPageTask{

	protected static BlockingQueue<Page> taskQueue =  new LinkedBlockingQueue<Page>(); 
	
	
	abstract void retry();
	
	abstract void handle(Page page);
	
}
