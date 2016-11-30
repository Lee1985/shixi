package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.ConfigInfoDao;
import com.bluemobi.www.data.model.system.ConfigInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.ConfigInfoService;

@Service
public class ConfigInfoServiceImpl extends BaseServiceImpl<ConfigInfo> implements ConfigInfoService{

	@Autowired
	private ConfigInfoDao configInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(configInfoDao);
	}
	
}