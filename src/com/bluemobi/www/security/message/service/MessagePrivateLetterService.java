package com.bluemobi.www.security.message.service;

import java.util.List;
import java.util.Map;

import com.bluemobi.www.data.model.message.MessagePrivateLetter;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface MessagePrivateLetterService extends BaseService<MessagePrivateLetter>{

	/**
	 * 查询私信记录
	 * @param params
	 * @param letterPage
	 * @return
	 */
	PageInfo<MessagePrivateLetter> selectPrivateRecords(Map<String, Object> params,PageInfo<MessagePrivateLetter> letterPage);

	/**
	 * 查询未读私信条数
	 * @param unreadParams
	 * @return
	 */
	List<Map<String,Object>> selectUnreadInfo(Map<String, Object> unreadParams);		

}
