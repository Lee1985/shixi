package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemRolePicDao;
import com.bluemobi.www.data.model.system.SystemRolePic;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemRolePicDaoImpl extends BaseDaoImpl<SystemRolePic> implements SystemRolePicDao{
	public SystemRolePicDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}