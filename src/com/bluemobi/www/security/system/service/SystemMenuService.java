package com.bluemobi.www.security.system.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.system.SystemMenu;
import com.bluemobi.www.security.base.service.BaseService;

public interface SystemMenuService extends BaseService<SystemMenu> {
	public List<SystemMenu> selectAllByPid(String pid);

	public List<SystemMenu> selectAllByPid(Integer pid);

	public int systemMenuDrag(String id,String pid,String moveType);

	public int selectMaxOrderListByPid(SystemMenu info);

	public int selectMaxOrderListByPid(Map<String, Object> info);
	
	public List<SystemMenu> selectAllByRole(SystemMenu info);
	
	public List<SystemMenu> selectAllByRoleLogin(SystemMenu info);
}
