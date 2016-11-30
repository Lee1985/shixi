package com.bluemobi.www.data.dao.member;

import java.util.List;

import com.bluemobi.www.data.dao.BaseDao;
import com.bluemobi.www.data.model.member.MemberInfo;
/**
 * 数据访问接口
 *
 */
public interface MemberInfoDao extends BaseDao<MemberInfo>{
	public String sqlNameSpace=MemberInfoDao.class.getName();

	public List<MemberInfo> selectByAccountAndPass(String account, String password);
	
	public List<MemberInfo> selectByAccount(String account,String id);

	public List<MemberInfo> selectByMobile(String mobile, String id);

	public List<MemberInfo> selectByNickname(String nickname, String id);

	public List<MemberInfo> selectByEmail(String email, String id);
}