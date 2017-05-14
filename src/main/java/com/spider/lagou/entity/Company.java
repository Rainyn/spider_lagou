package com.spider.lagou.entity;

import java.util.List;

//对用公司实体
public class Company {
	//公司URL
	private String url;
	//公司名
	private String companyName;
	//所在城市
	private String city;
	//公司行业
	private String industry;
	//公司规模
	private String scale;
	//融资情况
	private String financeLevel;
	
	public Company(){
		
	}
	

	public String getScale() {
		return scale;
	}


	public void setScale(String scale) {
		this.scale = scale;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getFinanceLevel() {
		return financeLevel;
	}

	public void setFinanceLevel(String financeLevel) {
		this.financeLevel = financeLevel;
	}
	
}
