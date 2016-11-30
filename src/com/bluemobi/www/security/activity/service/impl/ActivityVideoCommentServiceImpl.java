package com.bluemobi.www.security.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.activity.ActivityVideoCommentDao;
import com.bluemobi.www.data.model.activity.ActivityVideoComment;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.activity.service.ActivityVideoCommentService;

@Service
public class ActivityVideoCommentServiceImpl extends BaseServiceImpl<ActivityVideoComment> implements ActivityVideoCommentService{

	@Autowired
	private ActivityVideoCommentDao activityVideoCommentDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(activityVideoCommentDao);
	}
	
}