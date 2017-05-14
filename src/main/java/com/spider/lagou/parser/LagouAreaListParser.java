package com.spider.lagou.parser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spider.core.parser.AreaParser;
import com.spider.core.util.HttpClientUtil;
import com.spider.lagou.entity.Area;
import com.spider.lagou.entity.Page;
import com.spider.lagou.httpclient.Spider;
import com.spider.lagou.task.CompanyListTask;
/*
 * 解析拉勾网公司页面，提取出所有地区的名字和对应的URL 
 */
public class LagouAreaListParser implements AreaParser {

	public volatile static LagouAreaListParser instance;
	//单例模式
	public static LagouAreaListParser getInstance(){
		if(instance == null){
			synchronized(LagouAreaListParser.class){
				if(instance == null){
					instance = new LagouAreaListParser();
				}
			}
		}
		return instance;
	}
	//每次抓取到地区就放入到生产队列中
	public void startTask(String url) {
		String html = HttpClientUtil.httpGetRequest(url);
		//使用Jsoup解析抓取到的页面
		Document doc = Jsoup.parse(html);
		Elements content = doc.getElementsByClass("city_list");
		Elements links = content.select("a[href]");
		String linkHref,linkText;
		for (Element link : links) {	
			  linkHref = link.attr("href");
			  linkText = link.text();
			  Page page = new Page();
			  page.setUrl(linkHref);
			 // page.setHtml(linkText);
			  //调用线程执行者，开启一个新线程来运行
			  Spider.getInstance().getListPageThreadPool().execute(new CompanyListTask(page));
							
		}
		  
	}


}
