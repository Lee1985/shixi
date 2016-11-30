package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemHotspotCityDao;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;

@Service
public class SystemHotspotCityServiceImpl extends BaseServiceImpl<SystemHotspotCity> implements SystemHotspotCityService{
	
	@Autowired
	private SystemHotspotCityDao systemHotspotCityDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemHotspotCityDao);
	}
	
}