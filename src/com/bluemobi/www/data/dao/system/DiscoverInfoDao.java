package com.bluemobi.www.data.dao.system;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.system.DiscoverInfo;
/**
 * 数据访问接口
 *
 */
public interface DiscoverInfoDao extends BaseDao<DiscoverInfo>{
	public String sqlNameSpace=DiscoverInfoDao.class.getName();
}