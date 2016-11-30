package com.bluemobi.www.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.system.SystemCommonQuestionDao;
import com.bluemobi.www.data.model.system.SystemCommonQuestion;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemCommonQuestionService;

@Service
public class SystemCommonQuestionServiceImpl extends BaseServiceImpl<SystemCommonQuestion> implements SystemCommonQuestionService{

	@Autowired
	private SystemCommonQuestionDao systemCommonQuestionDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(systemCommonQuestionDao);
	}
	
}