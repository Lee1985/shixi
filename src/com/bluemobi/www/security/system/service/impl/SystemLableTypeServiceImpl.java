package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemLableTypeDao;
import com.bluemobi.www.data.model.system.SystemLableType;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemLableTypeService;

@Service
public class SystemLableTypeServiceImpl extends BaseServiceImpl<SystemLableType> implements SystemLableTypeService{

	@Autowired
	private SystemLableTypeDao systemLableTypeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemLableTypeDao);
	}
	
}