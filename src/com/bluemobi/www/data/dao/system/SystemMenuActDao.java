package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.SystemMenuAct;
/**
 * 数据访问接口
 *
 */
public interface SystemMenuActDao extends BaseDao<SystemMenuAct>{
	public String sqlNameSpace=SystemMenuActDao.class.getName();
}