package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitCategoryDao;
import com.bluemobi.www.data.model.recruit.RecruitCategory;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitCategoryService;

@Service
public class RecruitCategoryServiceImpl extends BaseServiceImpl<RecruitCategory> implements RecruitCategoryService{

	@Autowired
	private RecruitCategoryDao recruitCategoryDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitCategoryDao);
	}
	
}