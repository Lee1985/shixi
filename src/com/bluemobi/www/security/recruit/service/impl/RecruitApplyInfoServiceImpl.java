package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitApplyInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;

@Service
public class RecruitApplyInfoServiceImpl extends BaseServiceImpl<RecruitApplyInfo> implements RecruitApplyInfoService{

	@Autowired
	private RecruitApplyInfoDao recruitApplyInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitApplyInfoDao);
	}

	@Override
	public RecruitApplyInfo selectNext(String id) {
		RecruitApplyInfo temp = recruitApplyInfoDao.selectById(id);
		RecruitApplyInfo info = new RecruitApplyInfo();
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(1);
		pageInfo.setPageSize(1);
		info.setCreateDate(temp.getCreateDate());
		info.setStatus("0");
		recruitApplyInfoDao.selectAll(info, pageInfo);
		if(pageInfo.getTotal() > 0 ){
			return pageInfo.getRows().get(0);
		}
		return null;
	}
	
}