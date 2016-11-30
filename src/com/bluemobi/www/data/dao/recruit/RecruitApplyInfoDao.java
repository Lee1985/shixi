package com.bluemobi.www.data.dao.recruit;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
/**
 * 数据访问接口
 *
 */
public interface RecruitApplyInfoDao extends BaseDao<RecruitApplyInfo>{
	public String sqlNameSpace=RecruitApplyInfoDao.class.getName();
}