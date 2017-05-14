package com.spider.lagou.entity;

/*
 *	地区对象 
 */
public class Area {
	private String url;
	private String area;
	public Area(){}
	
	public Area(String url, String area) {
		this.url = url;
		this.area = area;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null || getClass()!=o.getClass())
			return false;
		if(this == o)
			return true;
		Area task = (Area)o;
		
		return url.equals(task.url);
	}
	
	@Override
	public int hashCode(){
		return url.hashCode();
	}
}
