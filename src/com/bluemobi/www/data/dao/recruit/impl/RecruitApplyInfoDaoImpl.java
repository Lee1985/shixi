package com.bluemobi.www.data.dao.recruit.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.recruit.RecruitApplyInfoDao;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class RecruitApplyInfoDaoImpl extends BaseDaoImpl<RecruitApplyInfo> implements RecruitApplyInfoDao{
	public RecruitApplyInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}