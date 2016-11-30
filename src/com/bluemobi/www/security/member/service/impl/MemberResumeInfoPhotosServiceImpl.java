package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeInfoPhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeInfoPhotos;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeInfoPhotosService;

@Service
public class MemberResumeInfoPhotosServiceImpl extends BaseServiceImpl<MemberResumeInfoPhotos> implements MemberResumeInfoPhotosService{

	@Autowired
	private MemberResumeInfoPhotosDao memberResumeInfoPhotosDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeInfoPhotosDao);
	}
	
}