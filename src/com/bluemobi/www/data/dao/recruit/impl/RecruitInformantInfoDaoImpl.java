package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitInformantInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitInformantInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitInformantInfoDaoImpl extends BaseDaoImpl<RecruitInformantInfo> implements RecruitInformantInfoDao{
	public RecruitInformantInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}