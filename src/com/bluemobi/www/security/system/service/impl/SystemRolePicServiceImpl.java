package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemRolePicDao;
import com.bluemobi.www.data.model.system.SystemRolePic;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemRolePicService;

@Service
public class SystemRolePicServiceImpl extends BaseServiceImpl<SystemRolePic> implements SystemRolePicService{

	@Autowired
	private SystemRolePicDao systemRolePicDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemRolePicDao);
	}
	
}