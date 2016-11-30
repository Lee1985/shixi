package com.bluemobi.www.data.model.message;

import java.io.Serializable;
import java.util.Date;

import com.bluemobi.www.data.model.BaseEntity;

/**
 * message_private_message_recent实体表()
 * @author lip
 * @date 2016-03-17 12:10:48
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MessagePrivateMessageRecent extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 用户id
	private java.lang.String rencentContactId; // 最近联系人Id
	private java.util.Date editTime; // 最近联系时间
	private java.lang.String readStatus; // 状态(0-有未读,1-已读)
	private String type;
	private String recentContent;
	private String unreadMessageCount;
	private String recentRoleId;
	
	private String nickname;
	private String contactNumber;
	private Date recentContactDate;
	private String recentContactDateStr;
		
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
     * 获取用户id属性
     *
     * @return memberId
     */
	public java.lang.String getMemberId() {
		return memberId;
	}
	
	/**
	 * 设置用户id属性
	 *
	 * @param memberId
	 */
	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}
	
	/**
     * 获取最近联系人Id属性
     *
     * @return rencentContactId
     */
	public java.lang.String getRencentContactId() {
		return rencentContactId;
	}
	
	/**
	 * 设置最近联系人Id属性
	 *
	 * @param rencentContactId
	 */
	public void setRencentContactId(java.lang.String rencentContactId) {
		this.rencentContactId = rencentContactId;
	}
	
	/**
     * 获取最近联系时间属性
     *
     * @return editTime
     */
	public java.util.Date getEditTime() {
		return editTime;
	}
	
	/**
	 * 设置最近联系时间属性
	 *
	 * @param editTime
	 */
	public void setEditTime(java.util.Date editTime) {
		this.editTime = editTime;
	}
	
	/**
     * 获取状态(0-有未读,1-已读)属性
     *
     * @return readStatus
     */
	public java.lang.String getReadStatus() {
		return readStatus;
	}
	
	/**
	 * 设置状态(0-有未读,1-已读)属性
	 *
	 * @param readStatus
	 */
	public void setReadStatus(java.lang.String readStatus) {
		this.readStatus = readStatus;
	}		

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}		

	public String getRecentContent() {
		return recentContent;
	}

	public void setRecentContent(String recentContent) {
		this.recentContent = recentContent;
	}		

	public String getUnreadMessageCount() {
		return unreadMessageCount;
	}

	public void setUnreadMessageCount(String unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}		

	public String getRecentRoleId() {
		return recentRoleId;
	}

	public void setRecentRoleId(String recentRoleId) {
		this.recentRoleId = recentRoleId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}		

	public Date getRecentContactDate() {
		return recentContactDate;
	}

	public void setRecentContactDate(Date recentContactDate) {
		this.recentContactDate = recentContactDate;
	}

	public String getRecentContactDateStr() {
		return recentContactDateStr;
	}

	public void setRecentContactDateStr(String recentContactDateStr) {
		this.recentContactDateStr = recentContactDateStr;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MessagePrivateMessageRecent");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", rencentContactId=").append(rencentContactId);
        sb.append(", editTime=").append(editTime);
        sb.append(", readStatus=").append(readStatus);
		sb.append('}');
        return sb.toString();
    }
    
}