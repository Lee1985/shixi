package com.bluemobi.www.data.dao.recruit.impl;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitRoleInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitRoleInfoDaoImpl extends BaseDaoImpl<RecruitRoleInfo> implements RecruitRoleInfoDao{
	
	public RecruitRoleInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
	
	@Override
	public List<RecruitRoleInfo> selectByRecruitIds(List<String> idList) {
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByRecruitIds", idList);
	}
	
	
}