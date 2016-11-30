package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberActionsInfoDao;
import com.bluemobi.www.data.model.member.MemberActionsInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberActionsInfoService;

@Service
public class MemberActionsInfoServiceImpl extends BaseServiceImpl<MemberActionsInfo> implements MemberActionsInfoService{

	@Autowired
	private MemberActionsInfoDao memberActionsInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberActionsInfoDao);
	}
	
}