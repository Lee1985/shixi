package com.bluemobi.www.security.recruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.recruit.RecruitInfoTempDao;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;

@Service
public class RecruitInfoTempServiceImpl extends BaseServiceImpl<RecruitInfoTemp> implements RecruitInfoTempService{

	@Autowired
	private RecruitInfoTempDao recruitInfoTempDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(recruitInfoTempDao);
	}

	@Override
	public RecruitInfoTemp selectNext(String type,String createDate) {
		RecruitInfoTemp info = new RecruitInfoTemp();
		PageInfo<RecruitInfoTemp> pageInfo = new PageInfo<RecruitInfoTemp>();
		pageInfo.setPage(1);
		pageInfo.setPageSize(1);
		info.setCreateDate(createDate);
		info.setStatus("0");
		recruitInfoTempDao.selectAll(info, pageInfo);
		if(pageInfo.getTotal() > 0 ){
			return pageInfo.getRows().get(0);
		}
		return null;
	}

	@Override
	public PageInfo<RecruitInfoTemp> selectAllByStatus(RecruitInfoTemp info,
			PageInfo<RecruitInfoTemp> pageInfo) {
		// TODO Auto-generated method stub
		return recruitInfoTempDao.selectAllByStatus(info, pageInfo);
	}
	
}