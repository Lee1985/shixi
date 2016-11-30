package com.bluemobi.www.data.model.message;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;

/**
 * message_private_letter实体表()
 * @author sundq
 * @date 2016-02-19 09:39:44
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MessagePrivateLetter extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String roleId; // 角色
	private java.lang.String recruitId; // 招募
	private java.lang.String senderId; // 发送人
	private java.lang.String receiveId; // 接收人
	private java.lang.String type; // 类型 1官方  2私人
	private java.lang.String sendContent; // 发送内容
	private java.lang.String sendDate; // 发送时间
	private java.lang.String address; // 面试地点
	private java.lang.String interviewDate;//面试地点
	private java.lang.String replyContent; // 回复内容
	private java.lang.String replyDate; // 回复时间
	private java.lang.String status; // 状态0-未读  1-已读  2-已回复
	private String recentlyId;
	
	
	private MemberInfo senderInfo;// 发信人
	private MemberInfo receiveInfo;// 收信人
	private RecruitInfo recruitInfo;// 招募信息
	private RecruitRoleInfo roleInfo;// 招募角色
	
	private String memberId;
	private String contactId;
	
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
     * 获取角色属性
     *
     * @return roleId
     */
	public java.lang.String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置角色属性
	 *
	 * @param roleId
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	
	/**
     * 获取招募属性
     *
     * @return recruitId
     */
	public java.lang.String getRecruitId() {
		return recruitId;
	}
	
	/**
	 * 设置招募属性
	 *
	 * @param recruitId
	 */
	public void setRecruitId(java.lang.String recruitId) {
		this.recruitId = recruitId;
	}
	
	/**
     * 获取发送人属性
     *
     * @return senderId
     */
	public java.lang.String getSenderId() {
		return senderId;
	}
	
	/**
	 * 设置发送人属性
	 *
	 * @param senderId
	 */
	public void setSenderId(java.lang.String senderId) {
		this.senderId = senderId;
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
     * 获取类型属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取发送内容属性
     *
     * @return sendContent
     */
	public java.lang.String getSendContent() {
		return sendContent;
	}
	
	/**
	 * 设置发送内容属性
	 *
	 * @param sendContent
	 */
	public void setSendContent(java.lang.String sendContent) {
		this.sendContent = sendContent;
	}
	
	/**
     * 获取面试地点属性
     *
     * @return address
     */
	public java.lang.String getAddress() {
		return address;
	}
	
	/**
	 * 设置面试地点属性
	 *
	 * @param address
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	/**
     * 获取回复内容属性
     *
     * @return replyContent
     */
	public java.lang.String getReplyContent() {
		return replyContent;
	}
	
	/**
	 * 设置回复内容属性
	 *
	 * @param replyContent
	 */
	public void setReplyContent(java.lang.String replyContent) {
		this.replyContent = replyContent;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MessagePrivateLetter");
        sb.append("{id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", recruitId=").append(recruitId);
        sb.append(", senderId=").append(senderId);
        sb.append(", receiveId=").append(receiveId);
        sb.append(", type=").append(type);
        sb.append(", sendContent=").append(sendContent);
        sb.append(", sendDate=").append(sendDate);
        sb.append(", address=").append(address);
        sb.append(", replyContent=").append(replyContent);
        sb.append(", replyDate=").append(replyDate);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getSendDate() {
		return sendDate;
	}

	public void setSendDate(java.lang.String sendDate) {
		this.sendDate = sendDate;
	}

	public java.lang.String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(java.lang.String replyDate) {
		this.replyDate = replyDate;
	}

	public MemberInfo getSenderInfo() {
		return senderInfo;
	}

	public void setSenderInfo(MemberInfo senderInfo) {
		this.senderInfo = senderInfo;
	}

	public MemberInfo getReceiveInfo() {
		return receiveInfo;
	}

	public void setReceiveInfo(MemberInfo receiveInfo) {
		this.receiveInfo = receiveInfo;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public RecruitRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RecruitRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(java.lang.String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getRecentlyId() {
		return recentlyId;
	}

	public void setRecentlyId(String recentlyId) {
		this.recentlyId = recentlyId;
	} 		
}