package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemMenuActUrlDao;
import com.bluemobi.www.data.model.system.SystemMenuActUrl;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemMenuActUrlDaoImpl extends BaseDaoImpl<SystemMenuActUrl> implements SystemMenuActUrlDao{
	public SystemMenuActUrlDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}