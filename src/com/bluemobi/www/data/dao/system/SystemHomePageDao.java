package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemHomePage;
/**
 * 数据访问接口
 *
 */
public interface SystemHomePageDao extends BaseDao<SystemHomePage>{
	public String sqlNameSpace=SystemHomePageDao.class.getName();
}