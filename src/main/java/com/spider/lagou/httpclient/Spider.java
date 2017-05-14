package com.spider.lagou.httpclient;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.spider.core.httpclient.IHttpClient;
import com.spider.core.util.Config;
import com.spider.core.util.LoggerUtil;
import com.spider.core.util.SimpleThreadPoolExecutor;
import com.spider.core.util.ThreadPoolMonitor;
import com.spider.lagou.parser.LagouAreaListParser;
import com.spider.lagou.task.CompanyListTask;
import com.spider.lagou.task.DetailCompanyTask;

//爬虫类(单例模式)
public class Spider implements IHttpClient {
	private volatile static Spider instance;
	private static Logger logger = LoggerUtil.getSimpleLogger(Spider.class);
	//统计公司数量
	public static AtomicInteger companyCount = new AtomicInteger(0);
	//停止标志位
	public static volatile boolean isStop = false;
	//公司列表页线程池
	private ThreadPoolExecutor listPageThreadPool;
	//公司详情页线程池
	private ThreadPoolExecutor detailPageThreadPool;
	
	private Spider(){
		initThreadPool();
	}
	
	public static Spider getInstance(){
		if(instance == null){
			synchronized(Spider.class){
				if(instance == null){
					instance = new Spider();
				}
			}
		}
		return instance;
	}
		
	public void startCrawl(String url){		
		//CompanyListTask.setSourceTask(LagouAreaListParser.getInstance().parserListArea(html));
		//初始化线程池
		initThreadPool();
		//开启消费者线程
		startComsumer();
		LagouAreaListParser.getInstance().startTask(url);
	}

	public void startCrawl() {
	
	}
	
	//初始化线程池
	private void initThreadPool(){
		//初始化线程池
		SimpleThreadPoolExecutor listCompany = new SimpleThreadPoolExecutor(Config.downloadThreadSize);
		listPageThreadPool = listCompany.getExecutor();
		//开启listPageThreadPool的监控线程
		//new Thread(new ThreadPoolMonitor(listPageThreadPool,"listPageThreadPool")).start();
		SimpleThreadPoolExecutor detailCompany = new SimpleThreadPoolExecutor(Config.comsumerThreadSize);
		detailPageThreadPool = detailCompany.getExecutor();
	}
	
	private void startComsumer(){
		for(int i=0; i<Config.comsumerThreadSize; ++i){
			getDetailPageThreadPool().execute(new DetailCompanyTask());
		}
		
	}
	
	public ThreadPoolExecutor getListPageThreadPool() {
		return listPageThreadPool;
	}

	public ThreadPoolExecutor getDetailPageThreadPool() {
		return detailPageThreadPool;
	}
	
	
}
