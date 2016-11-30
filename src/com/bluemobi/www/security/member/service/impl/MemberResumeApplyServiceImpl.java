package com.bluemobi.www.security.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeApplyDao;
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeApplyService;

@Service
public class MemberResumeApplyServiceImpl extends BaseServiceImpl<MemberResumeApply> implements MemberResumeApplyService{

	@Autowired
	private MemberResumeApplyDao memberResumeApplyDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeApplyDao);
	}

	@Override
	public MemberResumeApply selectEntityByMemberId(String memberId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", memberId);
		List<MemberResumeApply> list = memberResumeApplyDao.selectAll(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}