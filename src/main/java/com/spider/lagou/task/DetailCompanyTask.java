package com.spider.lagou.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spider.core.util.Config;
import com.spider.core.util.HttpClientUtil;
import com.spider.lagou.entity.Company;
import com.spider.lagou.entity.Page;
import com.spider.lagou.httpclient.Spider;

public class DetailCompanyTask extends AbstractPageTask implements Runnable {
	public static BlockingQueue<Company> detailCompany = new LinkedBlockingQueue<Company>();
	@Override
	public void run() {
		while(true){
			try {
				//消费者速度不高于生产者
				TimeUnit.SECONDS.sleep(15);
				Company tmpCompany = new Company();
				Page tmpPage = taskQueue.take();
				String html = tmpPage.getHtml();
				tmpCompany.setUrl(tmpPage.getUrl());
				//使用Jsoup解析抓取到的页面
				Document doc = Jsoup.parse(html);
				Elements contents = doc.getElementsByClass("item_content");
				//获取公司名
				Elements name = doc.getElementsByClass("hovertips");
				tmpCompany.setCompanyName(name.attr("title"));
				
				Elements detail = contents.select("ul > li > span");
				
				//获取公司行业
				tmpCompany.setIndustry(detail.get(0).html());
				//获取公司融资情况
				tmpCompany.setFinanceLevel(detail.get(1).html());
				//获取公司规模
				tmpCompany.setScale(detail.get(2).html());
				//获取公司所在城市
				tmpCompany.setCity(detail.get(3).html());
				detailCompany.put(tmpCompany);
				System.out.println(Thread.currentThread().getName()+"is consuming, now is "+detailCompany.size());
				//抓取够了就跑，真刺激
				if(detailCompany.size()>=Config.downloadPageCount){
					Spider.getInstance().getDetailPageThreadPool().shutdown();
					Spider.getInstance().getListPageThreadPool().shutdown();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e){
				continue;
			}
		}
	}

	@Override
	void retry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void handle(Page page) {
		// TODO Auto-generated method stub
		
	}

}
