package com.bluemobi.www.security.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberInfoDao;
import com.bluemobi.www.data.dao.system.SystemPictureInfoDao;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberInfoService;

@Service
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo> implements MemberInfoService{
	
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	@Autowired
	private SystemPictureInfoDao systemPictureInfoDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberInfoDao);
	}
	
	@Override
	public List<MemberInfo> selectByAccountAndPass(String account,String password) {		
		return memberInfoDao.selectByAccountAndPass(account,password);
	}
	
	@Override
	public List<MemberInfo> selectByMobile(String mobile, String id) {
		return memberInfoDao.selectByMobile(mobile,id);
	}
	
	@Override
	public List<MemberInfo> selectByNickname(String nickname, String id) {
		return memberInfoDao.selectByNickname(nickname,id);
	}
	
	@Override
	public List<MemberInfo> selectByEmail(String email, String id) {
		return memberInfoDao.selectByEmail(email,id);
	}
	
	@Override
	public List<MemberInfo> selectByIdsWithImage(List<String> idList) {
		List<MemberInfo> memberList = selectByIds(idList);
		List<String> imageIdList = new ArrayList<String>();
		for(MemberInfo memberInfo : memberList){
			imageIdList.add(memberInfo.getImgUuid());
		}
		List<SystemPictureInfo> picList = systemPictureInfoDao.selectByUuids(imageIdList);
		Map<String,SystemPictureInfo> picMap = new HashMap<String,SystemPictureInfo>();
		for(SystemPictureInfo systemPictureInfo : picList){
			picMap.put(systemPictureInfo.getUuid(), systemPictureInfo);
		}
		for(MemberInfo memberInfo : memberList){
			SystemPictureInfo picInfo = picMap.get(memberInfo.getImgUuid());
			memberInfo.setHeadImageUrl(picInfo.getUrlPath());
		}
		return memberList;
	}
	
	@Override
	public List<MemberInfo> selectAllWithImage(Map<String, Object> params) {
		List<MemberInfo> memberList = selectAll(params);
		List<String> imageIdList = new ArrayList<String>();
		for(MemberInfo memberInfo : memberList){
			imageIdList.add(memberInfo.getImgUuid());
		}
		List<SystemPictureInfo> picList = systemPictureInfoDao.selectByUuids(imageIdList);
		Map<String,SystemPictureInfo> picMap = new HashMap<String,SystemPictureInfo>();
		for(SystemPictureInfo systemPictureInfo : picList){
			picMap.put(systemPictureInfo.getUuid(), systemPictureInfo);
		}
		for(MemberInfo memberInfo : memberList){
			SystemPictureInfo picInfo = picMap.get(memberInfo.getImgUuid());
			memberInfo.setHeadImageUrl(picInfo.getUrlPath());
		}
		return memberList;
	}
	
	@Override
	public MemberInfo selectByIdWithImage(String id) {
		List<String> idList = new ArrayList<String>();
		idList.add(id);
		List<MemberInfo> memberList = selectByIdsWithImage(idList);
		MemberInfo memberInfo = null;
		if(memberList != null && !memberList.isEmpty()){
			memberInfo = memberList.get(0);			
		}
		return memberInfo;
	}
	
}