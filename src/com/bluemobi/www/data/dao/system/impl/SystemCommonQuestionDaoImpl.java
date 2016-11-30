package com.bluemobi.www.data.dao.system.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.system.SystemCommonQuestionDao;
import com.bluemobi.www.data.model.system.SystemCommonQuestion;
/**
 * 
 * @author mew
 *
 */
@Component
public class SystemCommonQuestionDaoImpl extends BaseDaoImpl<SystemCommonQuestion> implements SystemCommonQuestionDao{
	public SystemCommonQuestionDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}