package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemCommonReplyDao;
import com.bluemobi.www.data.model.system.SystemCommonReply;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemCommonReplyDaoImpl extends BaseDaoImpl<SystemCommonReply> implements SystemCommonReplyDao{
	public SystemCommonReplyDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}