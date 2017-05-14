package com.spider;

import com.spider.lagou.httpclient.Spider;

import com.spider.core.util.Config;

public class Main 
{
    public static void main( String[] args )
    {
    	String url = Config.startURL;
        System.out.println( "开始爬拉勾网，神明保佑啊" );
        Spider.getInstance().startCrawl(url);
    }
}
