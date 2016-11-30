package com.bluemobi.www.data.dao.activity.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.activity.ActivityVideoCommentDao;
import com.bluemobi.www.data.model.activity.ActivityVideoComment;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActivityVideoCommentDaoImpl extends BaseDaoImpl<ActivityVideoComment> implements ActivityVideoCommentDao{
	public ActivityVideoCommentDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}