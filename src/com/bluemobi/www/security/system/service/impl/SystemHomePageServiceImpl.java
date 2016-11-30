package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemHomePageDao;
import com.bluemobi.www.data.model.system.SystemHomePage;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemHomePageService;

@Service
public class SystemHomePageServiceImpl extends BaseServiceImpl<SystemHomePage> implements SystemHomePageService{

	@Autowired
	private SystemHomePageDao systemHomePageDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemHomePageDao);
	}
	
}