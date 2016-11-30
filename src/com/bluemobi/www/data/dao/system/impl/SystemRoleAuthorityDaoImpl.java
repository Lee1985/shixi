package com.bluemobi.www.data.dao.system.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemRoleAuthorityDao;
import com.bluemobi.www.data.model.system.SystemRoleAuthority;
import com.bluemobi.www.data.model.system.SystemUserInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemRoleAuthorityDaoImpl extends BaseDaoImpl<SystemRoleAuthority> implements SystemRoleAuthorityDao{
	
	public SystemRoleAuthorityDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectPermissUrl", info);
	}
}