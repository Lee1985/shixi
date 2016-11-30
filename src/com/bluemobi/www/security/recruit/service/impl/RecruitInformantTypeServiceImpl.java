package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitInformantTypeDao;
import com.bluemobi.www.data.model.recruit.RecruitInformantType;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitInformantTypeService;

@Service
public class RecruitInformantTypeServiceImpl extends BaseServiceImpl<RecruitInformantType> implements RecruitInformantTypeService{

	@Autowired
	private RecruitInformantTypeDao recruitInformantTypeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitInformantTypeDao);
	}
	
}