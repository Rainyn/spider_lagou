package com.spider.core.util;

import java.io.IOException;
import java.util.Properties;

//配置文件
public class Config {
	//是否存储到数据库 
	public static boolean dbEnable;
	//下载线程数
	public static int downloadThreadSize;
	//消费者（解析公司详情页）线程数量
	public static int comsumerThreadSize;
	//解析多少个公司页面就停止爬了
	public static int downloadPageCount;
	//http最大连接数
	public static int maxConnectSize;
	//爬虫入口
	public static String  startURL;
	//数据库名称
	public static String dbName;
	//数据库地址
	public static String dbHost;
	//数据库用户
	public static String dbUsername;
	//数据库密码
	public static String dbPassword;
	
	static {
		Properties p = new Properties();
		try {
			p.load(Config.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbEnable = Boolean.parseBoolean(p.getProperty("dbEnable"));
		downloadThreadSize = Integer.valueOf(p.getProperty("downloadThreadSize"));
		comsumerThreadSize = Integer.valueOf(p.getProperty("comsumerThreadSize"));
		downloadPageCount = Integer.valueOf(p.getProperty("downloadPageCount"));
		startURL = p.getProperty("startURL");
		maxConnectSize = Integer.valueOf(p.getProperty("maxConnectSize"));
		if (dbEnable){
            dbName = p.getProperty("db.name");
            dbHost = p.getProperty("db.host");
            dbUsername = p.getProperty("db.username");
            dbPassword = p.getProperty("db.password");          
        }
	}
	
}
