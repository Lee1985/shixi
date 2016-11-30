package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeTemplateDao;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeTemplateDaoImpl extends BaseDaoImpl<MemberResumeTemplate> implements MemberResumeTemplateDao{
	public MemberResumeTemplateDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}