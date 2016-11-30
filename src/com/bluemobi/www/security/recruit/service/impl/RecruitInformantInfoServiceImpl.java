package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitInformantInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitInformantInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitInformantInfoService;

@Service
public class RecruitInformantInfoServiceImpl extends BaseServiceImpl<RecruitInformantInfo> implements RecruitInformantInfoService{

	@Autowired
	private RecruitInformantInfoDao recruitInformantInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitInformantInfoDao);
	}
	
}