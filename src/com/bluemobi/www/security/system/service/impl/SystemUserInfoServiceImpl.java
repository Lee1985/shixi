package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemUserInfoDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemUserInfoService;

@Service
public class SystemUserInfoServiceImpl extends BaseServiceImpl<SystemUserInfo> implements SystemUserInfoService{

	@Autowired
	private SystemUserInfoDao systemUserInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemUserInfoDao);
	}
	
}