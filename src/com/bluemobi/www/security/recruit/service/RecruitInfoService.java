package com.bluemobi.www.security.recruit.service;


import java.util.Map;

import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface RecruitInfoService extends BaseService<RecruitInfo>{

	public PageInfo<RecruitInfo> selectInfoAll(RecruitInfo info, PageInfo<RecruitInfo> pageInfo);
	
	public void addViewNum(RecruitInfo recruitInfo);
	
	public PageInfo<RecruitInfo> batchSelectByMemberId(Map<String, Object> info, PageInfo<RecruitInfo> pageInfo);
	
	public int batchUpdateByMemberId(Map<String, Object> info);
}
