package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemAdvertisementInfoDao;
import com.bluemobi.www.data.model.system.SystemAdvertisementInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemAdvertisementInfoService;

@Service
public class SystemAdvertisementInfoServiceImpl extends BaseServiceImpl<SystemAdvertisementInfo> implements SystemAdvertisementInfoService{

	@Autowired
	private SystemAdvertisementInfoDao systemAdvertisementInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemAdvertisementInfoDao);
	}
	
}