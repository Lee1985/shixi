package com.bluemobi.www.security.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.activity.ActivityVideoInfoDao;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;

@Service
public class ActivityVideoInfoServiceImpl extends BaseServiceImpl<ActivityVideoInfo> implements ActivityVideoInfoService{

	@Autowired
	private ActivityVideoInfoDao activityVideoInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(activityVideoInfoDao);
	}

	@Override
	public int addShareNum(ActivityVideoInfo info) {
		// TODO Auto-generated method stub
		return activityVideoInfoDao.addShareNum(info);
	}
	
}