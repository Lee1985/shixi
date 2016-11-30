package com.bluemobi.www.security.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.activity.ActivityVideoLikeDao;
import com.bluemobi.www.data.model.activity.ActivityVideoLike;
import com.bluemobi.www.security.activity.service.ActivityVideoLikeService;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;

@Service
public class ActivityVideoLikeServiceImpl extends BaseServiceImpl<ActivityVideoLike> implements ActivityVideoLikeService{

	@Autowired
	private ActivityVideoLikeDao activityVideoLikeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(activityVideoLikeDao);
	}
	
}