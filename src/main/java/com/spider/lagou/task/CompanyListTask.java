package com.spider.lagou.task;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spider.core.util.HttpClientUtil;
import com.spider.lagou.entity.Area;
import com.spider.lagou.entity.Page;

/*
 *  功能：作为一个生产者，从源任务队列取数据解析后放到任务队列中给消费者 
 * 
 */
public class CompanyListTask extends AbstractPageTask implements Runnable {
	
	//源任务队列，存放着每个地区的名字和对应的URL
	//private BlockingQueue<Area> sourceTask;
	//Page类，存放要解析的URL
	private Page page;
	//private static AtomicInteger countThread = new AtomicInteger(0);
	
	public CompanyListTask(Page page){
		super();
		this.page = page;
	}
	@Override
	public void run() {
		getCompanyPage();
	}
	
	public void getCompanyPage(){
		//当前页面
		int pn = 0;
		//当前页面条数
		int pageSize = 0;
		Map<String,Object> params = new HashMap<>();
		String html = null;
		do{
			try {
				//每次把页码加1
				params.put("pn", ++pn);
				//获取该页码详情
				html = HttpClientUtil.httpPostRequest(page.getUrl(), params);
				//获取到每个公司对应的URL并入队到任务队列
				Document doc = Jsoup.parse(html);
				Element content = doc.getElementById("company_list");
				Elements tag = content.getElementsByTag("dt");
				pageSize = tag.size();
				Elements a = tag.select("a");
				String linkHref;
				//遍历结果，生成Page对象并保存到任务队列
				for(Element tmp : a){
					linkHref = tmp.attr("href");
					String tmpHtml = HttpClientUtil.httpGetRequest(linkHref);
					Page page = new Page();
					page.setHtml(tmpHtml);
					page.setUrl(linkHref);
					page.setStatusCode(200);
					taskQueue.put(page);
				}
				//System.out.println(Thread.currentThread().getName()+"is sleeping,now is"+pn);
				TimeUnit.SECONDS.sleep(10);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {//这个catch是预防最后一页也是16个的情况
				retry();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(pageSize == 16);
		System.out.println(Thread.currentThread().getName()+":"+taskQueue.size()+":"+pn);
	}

	@Override
	void retry() {
		//发生错误后，先暂停10秒再爬，因为已被拒绝，需要暂停
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void handle(Page page) {
		// TODO Auto-generated method stub
		
	}
	
	private void handleUrl(){
		
	}

}
