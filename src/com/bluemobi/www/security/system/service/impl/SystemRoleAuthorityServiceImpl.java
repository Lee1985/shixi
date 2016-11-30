package com.bluemobi.www.security.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluemobi.www.data.dao.system.SystemMenuActDao;
import com.bluemobi.www.data.dao.system.SystemRoleAuthorityDao;
import com.bluemobi.www.data.model.system.SystemMenuAct;
import com.bluemobi.www.data.model.system.SystemRoleAuthority;
import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.system.service.SystemRoleAuthorityService;
import com.bluemobi.www.utils.UUIDUtil;

@Service
public class SystemRoleAuthorityServiceImpl extends BaseServiceImpl<SystemRoleAuthority> implements
		SystemRoleAuthorityService {
	@Autowired
	private SystemRoleAuthorityDao dao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(dao);
	}
	@Autowired
	private SystemMenuActDao systemMenuActDao;

	@Transactional
	@Override
	public int saveAuthority(SystemRoleAuthority info) throws Exception{
		// TODO Auto-generated method stub
		int result = 0;
		SystemRoleAuthority deleteInfo = new SystemRoleAuthority();
		SystemRoleAuthority saveInfo = null;
		deleteInfo.setRoleId(info.getRoleId());
		delete(deleteInfo);
		String actId="";
		SystemMenuAct systemMenuAct=new SystemMenuAct();
		for (String id : info.getIds().split(",")) {
			saveInfo = new SystemRoleAuthority();
			saveInfo.setRoleId(info.getRoleId());
			saveInfo.setId(UUIDUtil.getUUID());
			if (id.indexOf("_")==-1) {
				saveInfo.setMenuId(id);
				result += insert(saveInfo);
				
				systemMenuAct.setMenuId(id);
				systemMenuAct.setActCode("menu");
				SystemMenuAct menuAct=systemMenuActDao.selectEntity(systemMenuAct);
				if (menuAct!=null) {
					saveInfo.setId(UUIDUtil.getUUID());
					saveInfo.setActId(menuAct.getId());
					result += insert(saveInfo);
				}
				
			} else {
				saveInfo.setMenuId(id.substring(0, id.indexOf("_")));
				saveInfo.setActId(id.substring(id.indexOf("_") + 1, id.length()));
				result += insert(saveInfo);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectPermissUrl(SystemUserInfo info) {
		return dao.selectPermissUrl(info);
	}

}