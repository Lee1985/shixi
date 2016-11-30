package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitInformantTypeDao;
import com.bluemobi.www.data.model.recruit.RecruitInformantType;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitInformantTypeDaoImpl extends BaseDaoImpl<RecruitInformantType> implements RecruitInformantTypeDao{
	public RecruitInformantTypeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}