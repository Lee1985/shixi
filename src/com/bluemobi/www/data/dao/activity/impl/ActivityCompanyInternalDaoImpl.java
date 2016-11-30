package com.bluemobi.www.data.dao.activity.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.activity.ActivityCompanyInternalDao;
import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
/**
 * 
 * @author mew
 *
 */
@Component
public class ActivityCompanyInternalDaoImpl extends BaseDaoImpl<ActivityCompanyInternal> implements ActivityCompanyInternalDao{
	public ActivityCompanyInternalDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}