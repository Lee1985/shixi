package com.bluemobi.www.security.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluemobi.www.data.dao.member.MemberResumeTemplatePhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
import com.bluemobi.www.security.base.service.impl.BaseServiceImpl;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;

@Service
public class MemberResumeTemplatePhotosServiceImpl extends BaseServiceImpl<MemberResumeTemplatePhotos> implements MemberResumeTemplatePhotosService{

	@Autowired
	private MemberResumeTemplatePhotosDao memberResumeTemplatePhotosDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(memberResumeTemplatePhotosDao);
	}
	
}