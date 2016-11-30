package com.bluemobi.www.security.recruit.service;

import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface RecruitApplyInfoService extends BaseService<RecruitApplyInfo>{

	public RecruitApplyInfo selectNext(String id);
}
