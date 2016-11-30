package com.bluemobi.www.security.member.service;

import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.security.base.service.BaseService;

public interface MemberResumeApplyService extends BaseService<MemberResumeApply>{

	MemberResumeApply selectEntityByMemberId(String memberId);

}
