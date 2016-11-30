package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemMenuActDao;
import com.bluemobi.www.data.model.system.SystemMenuAct;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemMenuActDaoImpl extends BaseDaoImpl<SystemMenuAct> implements SystemMenuActDao{
	public SystemMenuActDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}