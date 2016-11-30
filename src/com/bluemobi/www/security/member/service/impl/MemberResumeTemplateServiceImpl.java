package com.bluemobi.www.security.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeTemplateDao;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;

@Service
public class MemberResumeTemplateServiceImpl extends BaseServiceImpl<MemberResumeTemplate> implements MemberResumeTemplateService{

	@Autowired
	private MemberResumeTemplateDao memberResumeTemplateDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeTemplateDao);
	}

	@Override
	public MemberResumeTemplate selectEntityByMemberId(String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", id);
		List<MemberResumeTemplate> list = memberResumeTemplateDao.selectAll(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}