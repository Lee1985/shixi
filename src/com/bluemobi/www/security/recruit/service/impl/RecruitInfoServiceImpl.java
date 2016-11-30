package com.bluemobi.www.security.recruit.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;

@Service
public class RecruitInfoServiceImpl extends BaseServiceImpl<RecruitInfo> implements RecruitInfoService{

	@Autowired
	private RecruitInfoDao recruitInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitInfoDao);
	}
	
	public PageInfo<RecruitInfo> selectInfoAll(RecruitInfo info, PageInfo<RecruitInfo> pageInfo){
		return recruitInfoDao.selectInfoAll(info, pageInfo);
	}

	@Override
	public void addViewNum(RecruitInfo recruitInfo) {
		recruitInfoDao.addViewNum(recruitInfo);
	}

	@Override
	public PageInfo<RecruitInfo> batchSelectByMemberId(
			Map<String, Object> info, PageInfo<RecruitInfo> pageInfo) {
		return recruitInfoDao.batchSelectByMemberId(info, pageInfo);
	}

	@Override
	public int batchUpdateByMemberId(Map<String, Object> info) {
		return recruitInfoDao.batchUpdateByMemberId(info);
	}
}