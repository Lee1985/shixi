package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemRoleTypeDao;
import com.bluemobi.www.data.model.system.SystemRoleType;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemRoleTypeDaoImpl extends BaseDaoImpl<SystemRoleType> implements SystemRoleTypeDao{
	public SystemRoleTypeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}