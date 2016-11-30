package com.bluemobi.www.data.model.message;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;

/**
 * message_push_notification实体表()
 * @author sundq
 * @date 2016-02-19 13:54:57
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MessagePushNotification extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String type; // 类型
	private java.lang.String receiveId; // 接收人
	private java.lang.String content; // 内容
	private java.lang.String createDate; // 创建时间
	private java.lang.String status; // 状态0-未推送 1-已推送 
	private java.lang.String cid;
	
	private MemberInfo receiveInfo;//接收人
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
     * 获取标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取类型1-简历 2-视频 3-招募 4-认证属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取接收人属性
     *
     * @return receiveId
     */
	public java.lang.String getReceiveId() {
		return receiveId;
	}
	
	/**
	 * 设置接收人属性
	 *
	 * @param receiveId
	 */
	public void setReceiveId(java.lang.String receiveId) {
		this.receiveId = receiveId;
	}
	
	/**
     * 获取内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MessagePushNotification");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", receiveId=").append(receiveId);
        sb.append(", content=").append(content);
        sb.append(", createDate=").append(createDate);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public MemberInfo getReceiveInfo() {
		return receiveInfo;
	}

	public void setReceiveInfo(MemberInfo receiveInfo) {
		this.receiveInfo = receiveInfo;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getCid() {
		return cid;
	}

	public void setCid(java.lang.String cid) {
		this.cid = cid;
	}

}