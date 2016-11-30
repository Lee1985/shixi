package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemLableInfoDao;
import com.bluemobi.www.data.model.system.SystemLableInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemLableInfoService;

@Service
public class SystemLableInfoServiceImpl extends BaseServiceImpl<SystemLableInfo> implements SystemLableInfoService{

	@Autowired
	private SystemLableInfoDao systemLableInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemLableInfoDao);
	}
	
}