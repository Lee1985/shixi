package com.bluemobi.www.security.recruit.service;

import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface RecruitInfoTempService extends BaseService<RecruitInfoTemp>{

	public RecruitInfoTemp selectNext(String type,String createDate);
	
	public PageInfo<RecruitInfoTemp> selectAllByStatus(RecruitInfoTemp info, PageInfo<RecruitInfoTemp> pageInfo);
}
