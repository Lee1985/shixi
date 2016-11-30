package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemUserDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemUserService;

@Service
public class SystemUserServiceImpl extends BaseServiceImpl<SystemUserInfo> implements SystemUserService{
	@Autowired
	private SystemUserDao dao;
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}
	
}
