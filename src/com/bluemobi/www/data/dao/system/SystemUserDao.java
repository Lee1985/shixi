package com.bluemobi.www.data.dao.system;

import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;

public interface SystemUserDao extends BaseDao<SystemUserInfo>{
	public String sqlNameSpace=SystemUserDao.class.getName();
	public SystemUserInfo selectEntity(SystemUserInfo info);
	public SystemUserInfo selectEntity(Map<String, Object> info);
}
