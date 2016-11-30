package com.bluemobi.www.data.dao.member.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.member.MemberResumeTemplatePhotosDao;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
/**
 * 
 * @author mew
 *
 */
@Component
public class MemberResumeTemplatePhotosDaoImpl extends BaseDaoImpl<MemberResumeTemplatePhotos> implements MemberResumeTemplatePhotosDao{
	public MemberResumeTemplatePhotosDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}