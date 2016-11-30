package com.bluemobi.www.data.dao.system;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemRoleAuthority;
import com.bluemobi.www.data.model.system.SystemUserInfo;
/**
 * 数据访问接口
 *
 */
public interface SystemRoleAuthorityDao extends BaseDao<SystemRoleAuthority>{
	public String sqlNameSpace=SystemRoleAuthorityDao.class.getName();
	public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info);
}