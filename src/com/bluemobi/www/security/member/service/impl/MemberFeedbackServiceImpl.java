package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberFeedbackDao;
import com.bluemobi.www.data.model.member.MemberFeedback;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberFeedbackService;

@Service
public class MemberFeedbackServiceImpl extends BaseServiceImpl<MemberFeedback> implements MemberFeedbackService{

	@Autowired
	private MemberFeedbackDao memberFeedbackDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberFeedbackDao);
	}
	
}