package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemCommonReplyDao;
import com.bluemobi.www.data.model.system.SystemCommonReply;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemCommonReplyService;

@Service
public class SystemCommonReplyServiceImpl extends BaseServiceImpl<SystemCommonReply> implements SystemCommonReplyService{

	@Autowired
	private SystemCommonReplyDao systemCommonReplyDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemCommonReplyDao);
	}
	
}