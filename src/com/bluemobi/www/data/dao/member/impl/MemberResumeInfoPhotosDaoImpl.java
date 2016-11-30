package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeInfoPhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeInfoPhotos;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeInfoPhotosDaoImpl extends BaseDaoImpl<MemberResumeInfoPhotos> implements MemberResumeInfoPhotosDao{
	public MemberResumeInfoPhotosDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}