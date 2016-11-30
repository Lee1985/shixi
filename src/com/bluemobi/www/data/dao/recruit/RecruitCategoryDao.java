package com.bluemobi.www.data.dao.recruit;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.recruit.RecruitCategory;
/**
 * 数据访问接口
 *
 */
public interface RecruitCategoryDao extends BaseDao<RecruitCategory>{
	public String sqlNameSpace=RecruitCategoryDao.class.getName();
}