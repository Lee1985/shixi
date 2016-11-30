package com.bluemobi.www.security.member.service;

import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.security.base.service.BaseService;

public interface MemberResumeTemplateService extends BaseService<MemberResumeTemplate>{
	
	public MemberResumeTemplate selectEntityByMemberId(String id);

}
