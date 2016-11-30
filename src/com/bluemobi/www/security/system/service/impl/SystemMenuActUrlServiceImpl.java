package com.bluemobi.www.security.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemMenuActUrlDao;
import com.bluemobi.www.data.model.system.SystemMenuActUrl;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemMenuActUrlService;

@Service
public class SystemMenuActUrlServiceImpl extends BaseServiceImpl<SystemMenuActUrl> implements SystemMenuActUrlService{
	@Autowired
	private SystemMenuActUrlDao dao;
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}
}