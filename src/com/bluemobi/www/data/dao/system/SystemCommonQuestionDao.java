package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemCommonQuestion;
/**
 * 数据访问接口
 *
 */
public interface SystemCommonQuestionDao extends BaseDao<SystemCommonQuestion>{
	public String sqlNameSpace=SystemCommonQuestionDao.class.getName();
}