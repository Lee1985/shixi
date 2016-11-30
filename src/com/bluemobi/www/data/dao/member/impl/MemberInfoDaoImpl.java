package com.bluemobi.www.data.dao.member.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberInfoDao;
import com.bluemobi.www.data.model.member.MemberInfo;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberInfoDaoImpl extends BaseDaoImpl<MemberInfo> implements MemberInfoDao{
	
	public MemberInfoDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
	
	@Override
	public List<MemberInfo> selectByAccountAndPass(String account,String password) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("password", password);
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByAccountAndPass", params);
	}
	
	@Override
	public List<MemberInfo> selectByAccount(String account, String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("id", id);
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByAccount", params);
	}

	@Override
	public List<MemberInfo> selectByMobile(String mobile, String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", mobile);
		params.put("id", id);
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByMobile", params);
	}

	@Override
	public List<MemberInfo> selectByNickname(String nickname, String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nickname", nickname);
		params.put("id", id);
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByNickname", params);
	}
	
	@Override
	public List<MemberInfo> selectByEmail(String email, String id) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("email", email);
		params.put("id", id);
		return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByEmail", params);
	}
}