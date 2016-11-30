package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.DiscoverInfoDao;
import com.bluemobi.www.data.model.system.DiscoverInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.DiscoverInfoService;

@Service
public class DiscoverInfoServiceImpl extends BaseServiceImpl<DiscoverInfo> implements DiscoverInfoService{

	@Autowired
	private DiscoverInfoDao discoverInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(discoverInfoDao);
	}
	
}