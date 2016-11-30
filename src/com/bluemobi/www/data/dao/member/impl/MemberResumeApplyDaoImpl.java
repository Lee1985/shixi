package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeApplyDao;
import com.bluemobi.www.data.model.member.MemberResumeApply;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeApplyDaoImpl extends BaseDaoImpl<MemberResumeApply> implements MemberResumeApplyDao{
	public MemberResumeApplyDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}