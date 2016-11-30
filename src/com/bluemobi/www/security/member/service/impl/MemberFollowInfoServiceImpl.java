package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberFollowInfoDao;
import com.bluemobi.www.data.model.member.MemberFollowInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;

@Service
public class MemberFollowInfoServiceImpl extends BaseServiceImpl<MemberFollowInfo> implements MemberFollowInfoService{

	@Autowired
	private MemberFollowInfoDao memberFollowInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberFollowInfoDao);
	}
	
}