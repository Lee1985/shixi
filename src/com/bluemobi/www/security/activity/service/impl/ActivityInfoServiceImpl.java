package com.bluemobi.www.security.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.activity.ActivityInfoDao;
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.activity.service.ActivityInfoService;

@Service
public class ActivityInfoServiceImpl extends BaseServiceImpl<ActivityInfo> implements ActivityInfoService{

	@Autowired
	private ActivityInfoDao activityInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(activityInfoDao);
	}
	
}