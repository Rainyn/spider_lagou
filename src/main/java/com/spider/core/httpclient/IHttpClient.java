package com.spider.core.httpclient;

public interface IHttpClient {
	
    void startCrawl(String url);
    /**
     * 爬虫入口
     */
    void startCrawl();
}
