package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_full_text实体表()
 * @author sundq
 * @date 2016-02-14 13:32:36
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemFullText extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.Integer type; // 1用户协议
	private java.lang.String title; // 
	private java.lang.String msgContent; // 
	/**
     * 获取属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取1注册协议2咨询客服3等级说明4关于够了啦属性
     *
     * @return type
     */
	public java.lang.Integer getType() {
		return type;
	}
	
	/**
	 * 设置1注册协议2咨询客服3等级说明4关于够了啦属性
	 *
	 * @param type
	 */
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	/**
     * 获取属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取属性
     *
     * @return msgContent
     */
	public java.lang.String getMsgContent() {
		return msgContent;
	}
	
	/**
	 * 设置属性
	 *
	 * @param msgContent
	 */
	public void setMsgContent(java.lang.String msgContent) {
		this.msgContent = msgContent;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemFullText");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", title=").append(title);
        sb.append(", msgContent=").append(msgContent);
		sb.append('}');
        return sb.toString();
    }
    
}