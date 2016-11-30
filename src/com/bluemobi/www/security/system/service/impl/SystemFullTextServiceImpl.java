package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemFullTextDao;
import com.bluemobi.www.data.model.system.SystemFullText;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemFullTextService;

@Service
public class SystemFullTextServiceImpl extends BaseServiceImpl<SystemFullText> implements SystemFullTextService{

	@Autowired
	private SystemFullTextDao systemFullTextDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemFullTextDao);
	}
	
}