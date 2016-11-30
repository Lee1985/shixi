package com.bluemobi.www.security.system.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.system.SystemRoleAuthority;
import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface SystemRoleAuthorityService extends BaseService<SystemRoleAuthority>{
	public int saveAuthority(SystemRoleAuthority info) throws Exception;
	public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info);
}
