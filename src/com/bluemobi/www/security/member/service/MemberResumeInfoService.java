package com.bluemobi.www.security.member.service;

import com.bluemobi.www.data.model.member.MemberResumeInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface MemberResumeInfoService extends BaseService<MemberResumeInfo>{

	public MemberResumeInfo selectEntityByMemberId(String id);
}
