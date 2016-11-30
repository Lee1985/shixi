package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemUserInfoDao extends BaseDao<SystemUserInfo>{
	public String sqlNameSpace=SystemUserInfoDao.class.getName();
}