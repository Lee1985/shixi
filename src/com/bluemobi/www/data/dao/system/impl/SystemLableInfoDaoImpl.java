package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemLableInfoDao;
import com.bluemobi.www.data.model.system.SystemLableInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemLableInfoDaoImpl extends BaseDaoImpl<SystemLableInfo> implements SystemLableInfoDao{
	public SystemLableInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}