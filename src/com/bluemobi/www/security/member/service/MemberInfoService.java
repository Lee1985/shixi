package com.bluemobi.www.security.member.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface MemberInfoService extends BaseService<MemberInfo>{
	
	public List<MemberInfo> selectByAccountAndPass(String account,String password);
	
	public List<MemberInfo> selectByMobile(String mobile,String id);
	
	public List<MemberInfo> selectByNickname(String nickname,String id);
	
	public List<MemberInfo> selectByEmail(String email,String id);
	
	/**
	 * 根据id列表批量查询带有用户头像的用户信息
	 * @param idList
	 * @return
	 */
	public MemberInfo selectByIdWithImage(String id);
	
	/**
	 * 根据id列表批量查询带有用户头像的用户信息
	 * @param idList
	 * @return
	 */
	public List<MemberInfo> selectByIdsWithImage(List<String> idList);
	
	/**
	 * 根据查询条件查询
	 * @param params
	 * @return
	 */
	public List<MemberInfo> selectAllWithImage(Map<String,Object> params);
}
