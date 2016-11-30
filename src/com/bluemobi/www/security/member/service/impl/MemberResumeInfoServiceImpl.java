package com.bluemobi.www.security.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeInfoDao;
import com.bluemobi.www.data.model.member.MemberResumeInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeInfoService;

@Service
public class MemberResumeInfoServiceImpl extends BaseServiceImpl<MemberResumeInfo> implements MemberResumeInfoService{

	@Autowired
	private MemberResumeInfoDao memberResumeInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeInfoDao);
	}

	@Override
	public MemberResumeInfo selectEntityByMemberId(String id) {
		Map map = new HashMap();
		map.put("memberId", id);
		List list = memberResumeInfoDao.selectAll(map);
		if(list != null && list.size() > 0){
			return (MemberResumeInfo) list.get(0);
		}
		return new MemberResumeInfo();
	}
	
}