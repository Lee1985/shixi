package com.bluemobi.www.data.dao.member;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
/**
 * 数据访问接口
 *
 */
public interface MemberResumeTemplateDao extends BaseDao<MemberResumeTemplate>{
	public String sqlNameSpace=MemberResumeTemplateDao.class.getName();
}