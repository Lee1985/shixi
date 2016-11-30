package com.bluemobi.www.data.dao.activity;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.activity.ActivityVideoComment;
/**
 * 数据访问接口
 *
 */
public interface ActivityVideoCommentDao extends BaseDao<ActivityVideoComment>{
	public String sqlNameSpace=ActivityVideoCommentDao.class.getName();
}