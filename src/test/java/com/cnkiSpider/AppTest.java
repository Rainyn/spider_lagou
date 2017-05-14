package com.cnkiSpider;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.spider.core.util.HttpClientUtil;
import com.spider.lagou.entity.Page;

/**
 * Unit test for simple App.
 */

//要做的事：去重，condition
public class AppTest {
	@Test
	public void testgongsi() {
		BlockingQueue<Page> taskQueue = new LinkedBlockingQueue<Page>();
		//String html = HttpClientUtil.httpGetRequest("https://www.lagou.com/gongsi/2-0-0?pn=4");
		Map<String,Object> params = new HashMap<>();
		int pn=1;
		int pageSize = 1;
		String html = null;
		Element content = null;
		Document doc = null;
		Elements tag;
		do{
			try {
				params.put("pn", ++pn);
				html = HttpClientUtil.httpPostRequest("https://www.lagou.com/gongsi/2-0-0", params);
				//System.out.println(html);
				doc = Jsoup.parse(html);
				
				content = doc.getElementById("company_list");
				
				tag = content.getElementsByTag("dt");
				pageSize = tag.size();
				
				//System.out.println(pageSize);
				Elements a = tag.select("a");
				String linkHref;
				for(Element tmp : a){
					linkHref = tmp.attr("href");
					//System.out.println(linkHref);
					String tmpHtml = HttpClientUtil.httpGetRequest(linkHref);
					Page page = new Page();
					page.setHtml(tmpHtml);
					page.setUrl(linkHref);
					page.setStatusCode(404);
					taskQueue.put(page);
				}
				System.out.println(pn);
				TimeUnit.SECONDS.sleep(10);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				//e.printStackTrace();
				System.out.println(doc);
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(pageSize == 16);
		
		System.out.println(taskQueue.size());
	}
	@Test
	public void testDetailCompany(){
		String html = HttpClientUtil.httpGetRequest("https://www.lagou.com/gongsi/41570.html");
		Document doc = Jsoup.parse(html);
		Elements contents = doc.getElementsByClass("item_content");
		
		Elements name = doc.getElementsByClass("hovertips");
		//System.out.println(name.attr("title"));
		Elements li = contents.select("ul > li > span");
		for(Element tmp : li){
			if(tmp.html().length()!=0){
				System.out.println(tmp.html());
			}
		}
	}
}
