package com.bluemobi.www.data.dao.recruit;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.page.PageInfo;
/**
 * 数据访问接口
 *
 */
public interface RecruitInfoTempDao extends BaseDao<RecruitInfoTemp>{
	public String sqlNameSpace=RecruitInfoTempDao.class.getName();
	
	public PageInfo<RecruitInfoTemp> selectAllByStatus(RecruitInfoTemp info,
			PageInfo<RecruitInfoTemp> pageInfo);
}