package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemFullText;
/**
 * 数据访问接口
 *
 */
public interface SystemFullTextDao extends BaseDao<SystemFullText>{
	public String sqlNameSpace=SystemFullTextDao.class.getName();
}