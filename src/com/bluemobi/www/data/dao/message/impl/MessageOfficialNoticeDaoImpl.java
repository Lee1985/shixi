package com.bluemobi.www.data.dao.message.impl;
import org.springframework.stereotype.Component;
import com.bluemobi.www.data.dao.BaseDaoImpl;
import com.bluemobi.www.data.dao.message.MessageOfficialNoticeDao;
import com.bluemobi.www.data.model.message.MessageOfficialNotice;
/**
 * 
 * @author mew
 *
 */
@Component
public class MessageOfficialNoticeDaoImpl extends BaseDaoImpl<MessageOfficialNotice> implements MessageOfficialNoticeDao{
	public MessageOfficialNoticeDaoImpl(){
		setSql_name_space(sqlNameSpace);
	}
}