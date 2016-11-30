package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemMenuActDao;
import com.bluemobi.www.data.model.system.SystemMenuAct;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemMenuActService;

@Service
public class SystemMenuActServiceImpl extends BaseServiceImpl<SystemMenuAct> implements SystemMenuActService{
	@Autowired
	private SystemMenuActDao dao;
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}

}