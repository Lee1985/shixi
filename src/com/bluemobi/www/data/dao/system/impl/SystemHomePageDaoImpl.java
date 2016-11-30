package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemHomePageDao;
import com.bluemobi.www.data.model.system.SystemHomePage;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemHomePageDaoImpl extends BaseDaoImpl<SystemHomePage> implements SystemHomePageDao{
	public SystemHomePageDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}