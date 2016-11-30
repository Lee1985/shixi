package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeApplyPhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeApplyPhotos;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeApplyPhotosDaoImpl extends BaseDaoImpl<MemberResumeApplyPhotos> implements MemberResumeApplyPhotosDao{
	public MemberResumeApplyPhotosDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}