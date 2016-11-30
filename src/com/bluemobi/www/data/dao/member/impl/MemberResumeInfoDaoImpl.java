package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeInfoDao;
import com.bluemobi.www.data.model.member.MemberResumeInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeInfoDaoImpl extends BaseDaoImpl<MemberResumeInfo> implements MemberResumeInfoDao{
	public MemberResumeInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}