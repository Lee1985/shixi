package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeApplyPhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeApplyPhotos;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeApplyPhotosService;

@Service
public class MemberResumeApplyPhotosServiceImpl extends BaseServiceImpl<MemberResumeApplyPhotos> implements MemberResumeApplyPhotosService{

	@Autowired
	private MemberResumeApplyPhotosDao memberResumeApplyPhotosDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeApplyPhotosDao);
	}
	
}