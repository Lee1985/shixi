package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemFullTextDao;
import com.bluemobi.www.data.model.system.SystemFullText;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemFullTextDaoImpl extends BaseDaoImpl<SystemFullText> implements SystemFullTextDao{
	public SystemFullTextDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}