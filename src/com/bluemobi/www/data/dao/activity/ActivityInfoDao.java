package com.bluemobi.www.data.dao.activity;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.activity.ActivityInfo;
/**
 * 数据访问接口
 *
 */
public interface ActivityInfoDao extends BaseDao<ActivityInfo>{
	public String sqlNameSpace=ActivityInfoDao.class.getName();
}