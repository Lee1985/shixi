package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitRoleInfoTempDao;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitRoleInfoTempDaoImpl extends BaseDaoImpl<RecruitRoleInfoTemp> implements RecruitRoleInfoTempDao{
	public RecruitRoleInfoTempDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}