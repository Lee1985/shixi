package com.bluemobi.www.data.dao.activity;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.activity.ActivityVideoLike;
/**
 * 数据访问接口
 *
 */
public interface ActivityVideoLikeDao extends BaseDao<ActivityVideoLike>{
	public String sqlNameSpace=ActivityVideoLikeDao.class.getName();
}