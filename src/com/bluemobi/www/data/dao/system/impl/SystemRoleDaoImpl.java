package com.bluemobi.www.data.dao.system.impl;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemRoleDao;
import com.bluemobi.www.data.model.system.SystemRole;

@Component
public class SystemRoleDaoImpl extends BaseDaoImpl<SystemRole> implements
		SystemRoleDao {
	public SystemRoleDaoImpl() {
		setSql_name_space(sqlNameSpace);
	}
}
