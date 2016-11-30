package com.bluemobi.www.security.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.activity.ActivityCompanyInternalDao;
import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.activity.service.ActivityCompanyInternalService;

@Service
public class ActivityCompanyInternalServiceImpl extends BaseServiceImpl<ActivityCompanyInternal> implements ActivityCompanyInternalService{

	@Autowired
	private ActivityCompanyInternalDao activityCompanyInternalDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(activityCompanyInternalDao);
	}
	
}