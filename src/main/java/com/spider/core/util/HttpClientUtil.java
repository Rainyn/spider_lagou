package com.spider.core.util;

import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.net.URISyntaxException;  
import java.util.ArrayList;  
import java.util.Map;  
  
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpRequestBase;  
import org.apache.http.client.utils.URIBuilder;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
  
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
  
public class HttpClientUtil {  
	private static String cookie = "user_trace_token=20170505134103-95a2ea0d8f31406abd949db8289ec82a; LGUID=20170505134103-6d705d3f-3155-11e7-b5ae-5254005c3644; JSESSIONID=AF42CF73705C050F108D22826FD3580D; _gat=1; PRE_UTM=; PRE_HOST=www.baidu.com; PRE_SITE=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DKmNs3-pKg6O1NfQ4dFHWWHWlQ-ArnBMBjXvRdxUZCMC%26wd%3D%26eqid%3D990f72e30007a85a00000006590d30ea; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; index_location_city=%E6%B7%B1%E5%9C%B3; TG-TRACK-CODE=index_company; _gid=GA1.2.1668903491.1494036997; _ga=GA1.2.638037241.1493962929; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1493967810,1494036786,1494036793,1494036997; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1494036997; LGSID=20170506101203-653f84f8-3201-11e7-b722-5254005c3644; LGRID=20170506101527-ded8f875-3201-11e7-b722-5254005c3644";
	private static PoolingHttpClientConnectionManager cm;  
    private static String EMPTY_STR = "";  
    private static String UTF_8 = "UTF-8";  
    
    public final static String[] userAgentArray = new String[]{
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2623.110 Safari/537.36",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36"
    };
  
    private static void init() {  
        if (cm == null) {  
            cm = new PoolingHttpClientConnectionManager();  
            cm.setMaxTotal(Config.maxConnectSize);// 整个连接池最大连接数  
            cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
            
        }  
    }  
  
    /** 
     * 通过连接池获取HttpClient 
     *  
     * @return 
     */  
    private static CloseableHttpClient getHttpClient() {  
        init();  
        //HttpHost proxy = new HttpHost("118.178.227.171", 80 , "HTTP");  
        return HttpClients.custom().setConnectionManager(cm).build();  
    }  
  
    /** 
     *  
     * @param url 
     * @return 
     */  
    public static String httpGetRequest(String url) {  
        HttpGet httpGet = new HttpGet(url);  
        return getResult(httpGet);  
    }  
  
    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {  
        URIBuilder ub = new URIBuilder();  
        ub.setPath(url);  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        ub.setParameters(pairs);  
  
        HttpGet httpGet = new HttpGet(ub.build());  
        return getResult(httpGet);  
    }  
  
    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)  
            throws URISyntaxException {  
        URIBuilder ub = new URIBuilder();  
        ub.setPath(url);  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        ub.setParameters(pairs);  
  
        HttpGet httpGet = new HttpGet(ub.build());  
        for (Map.Entry<String, Object> param : headers.entrySet()) {  
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }  
        return getResult(httpGet);  
    }  
  
    public static String httpPostRequest(String url) {  
        HttpPost httpPost = new HttpPost(url);  
        return getResult(httpPost);  
    }  
  
    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));  
        return getResult(httpPost);  
    }  
  
    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)  
            throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
  
        for (Map.Entry<String, Object> param : headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }  
  
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));  
  
        return getResult(httpPost);  
    }  
  
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {  
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, Object> param : params.entrySet()) {  
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));  
        }  
  
        return pairs;  
    }  
  
    /** 
     * 处理Http请求 
     *  
     * @param request 
     * @return 
     */  
    private static String getResult(HttpRequestBase request) {  
        // CloseableHttpClient httpClient = HttpClients.createDefault(); 
    	request.addHeader("Cookie",cookie);
    	String baiduSpider = "Mozilla/5.0+(compatible;+Baiduspider/2.0;++http://www.baidu.com/search/spider.html)";
    	request.setHeader("User-Agent", baiduSpider);
    	//request.addHeader("x-forwarded-for","220.181.108.91");
    	CloseableHttpClient httpClient = getHttpClient();  
        try {  
            CloseableHttpResponse response = httpClient.execute(request);  
            // response.getStatusLine().getStatusCode();  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                // long len = entity.getContentLength();// -1 表示长度未知  
                String result = EntityUtils.toString(entity);  
                response.close();  
                // httpClient.close();  
                return result;  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	
        }  
  
        return EMPTY_STR;  
    }  

  
}  
