package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemLableTypeDao;
import com.bluemobi.www.data.model.system.SystemLableType;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemLableTypeDaoImpl extends BaseDaoImpl<SystemLableType> implements SystemLableTypeDao{
	public SystemLableTypeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}