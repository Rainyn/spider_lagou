package com.spider.core.parser;

import java.util.List;

import com.spider.lagou.entity.Page;

public interface ListCompanyParser extends Parser {
	List parseListCompany(Page page);
}
