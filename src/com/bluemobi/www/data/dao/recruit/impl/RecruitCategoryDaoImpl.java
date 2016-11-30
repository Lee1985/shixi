package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitCategoryDao;
import com.bluemobi.www.data.model.recruit.RecruitCategory;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitCategoryDaoImpl extends BaseDaoImpl<RecruitCategory> implements RecruitCategoryDao{
	public RecruitCategoryDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}