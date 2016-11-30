package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberFeedbackDao;
import com.bluemobi.www.data.model.member.MemberFeedback;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberFeedbackDaoImpl extends BaseDaoImpl<MemberFeedback> implements MemberFeedbackDao{
	public MemberFeedbackDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}