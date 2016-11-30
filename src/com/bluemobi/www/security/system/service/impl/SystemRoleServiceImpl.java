package com.bluemobi.www.security.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemRoleDao;
import com.bluemobi.www.data.model.system.SystemRole;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemRoleService;

@Service
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements SystemRoleService {
	@Autowired
	private SystemRoleDao dao;

	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}
	
}
