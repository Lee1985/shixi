package com.bluemobi.www.data.dao.activity.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.activity.ActivityInfoDao;
import com.bluemobi.www.data.model.activity.ActivityInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActivityInfoDaoImpl extends BaseDaoImpl<ActivityInfo> implements ActivityInfoDao{
	public ActivityInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}