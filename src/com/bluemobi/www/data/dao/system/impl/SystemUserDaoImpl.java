package com.bluemobi.www.data.dao.system.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemUserDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;
@Component
public class SystemUserDaoImpl extends BaseDaoImpl<SystemUserInfo> implements SystemUserDao{
	public SystemUserDaoImpl() {
		setSql_name_space(sqlNameSpace);
	}
	@Override
	public SystemUserInfo selectEntity(SystemUserInfo info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectOne(sql_name_space+"."+"selectEntity", info);
	}
	@Override
	public SystemUserInfo selectEntity(Map<String, Object> info) {
		// TODO Auto-generated method stub
		return dao.getSqlSessionTemplate().selectOne(sql_name_space+"."+"selectEntity", info);
	}

}
