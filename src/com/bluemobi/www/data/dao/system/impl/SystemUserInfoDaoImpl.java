package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemUserInfoDao;
import com.bluemobi.www.data.model.system.SystemUserInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemUserInfoDaoImpl extends BaseDaoImpl<SystemUserInfo> implements SystemUserInfoDao{
	public SystemUserInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}