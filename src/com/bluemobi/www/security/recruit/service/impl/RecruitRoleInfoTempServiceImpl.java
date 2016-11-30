package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitRoleInfoTempDao;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;

@Service
public class RecruitRoleInfoTempServiceImpl extends BaseServiceImpl<RecruitRoleInfoTemp> implements RecruitRoleInfoTempService{

	@Autowired
	private RecruitRoleInfoTempDao recruitRoleInfoTempDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitRoleInfoTempDao);
	}
	
}