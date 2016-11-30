package com.bluemobi.www.data.dao.activity;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
/**
 * 数据访问接口
 *
 */
public interface ActivityVideoInfoDao extends BaseDao<ActivityVideoInfo>{
	public String sqlNameSpace=ActivityVideoInfoDao.class.getName();
	
	public int addShareNum(ActivityVideoInfo info);
}