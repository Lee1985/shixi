package com.bluemobi.www.data.dao.activity.impl;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.activity.ActivityVideoInfoDao;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActivityVideoInfoDaoImpl extends BaseDaoImpl<ActivityVideoInfo> implements ActivityVideoInfoDao{
	public ActivityVideoInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}

	@Override
	public int addShareNum(ActivityVideoInfo info) {
		return dao.getSqlSessionTemplate().update(sql_name_space + ".addShareNum",
				info);
	}
}