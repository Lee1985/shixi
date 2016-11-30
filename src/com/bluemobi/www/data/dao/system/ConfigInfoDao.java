package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.ConfigInfo;
/**
 * 数据访问接口
 *
 */
public interface ConfigInfoDao extends BaseDao<ConfigInfo>{
	public String sqlNameSpace=ConfigInfoDao.class.getName();
}