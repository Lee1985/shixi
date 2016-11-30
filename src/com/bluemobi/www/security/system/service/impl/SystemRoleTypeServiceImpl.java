package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemRoleTypeDao;
import com.bluemobi.www.data.model.system.SystemRoleType;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemRoleTypeService;

@Service
public class SystemRoleTypeServiceImpl extends BaseServiceImpl<SystemRoleType> implements SystemRoleTypeService{

	@Autowired
	private SystemRoleTypeDao systemRoleTypeDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemRoleTypeDao);
	}
	
}