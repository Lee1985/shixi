package com.bluemobi.www.data.model.member;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * member_feedback实体表()
 * @author sundq
 * @date 2016-02-14 10:27:14
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberFeedback extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 用户ID
	private java.lang.String mobile; // 手机
	private java.lang.String content; // 内容
	private java.lang.String createDate; // 反馈时间
	private java.lang.String status; // 状态
	
	private MemberInfo memberInfo;//用户信息
	
	private String memberName;
	private String startDate;
	private String endDate;
	private String reply;//回复内容
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
     * 获取用户ID属性
     *
     * @return memberId
     */
	public java.lang.String getMemberId() {
		return memberId;
	}
	
	/**
	 * 设置用户ID属性
	 *
	 * @param memberId
	 */
	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}
	
	/**
     * 获取手机属性
     *
     * @return mobile
     */
	public java.lang.String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置手机属性
	 *
	 * @param mobile
	 */
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
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
	
	
	/**
     * 获取状态属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置状态属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberFeedback");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", mobile=").append(mobile);
        sb.append(", content=").append(content);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
    
}