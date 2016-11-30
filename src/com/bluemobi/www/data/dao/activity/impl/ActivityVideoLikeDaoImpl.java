package com.bluemobi.www.data.dao.activity.impl;
import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.activity.ActivityVideoLikeDao;
import com.bluemobi.www.data.model.activity.ActivityVideoLike;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActivityVideoLikeDaoImpl extends BaseDaoImpl<ActivityVideoLike> implements ActivityVideoLikeDao{
	public ActivityVideoLikeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}