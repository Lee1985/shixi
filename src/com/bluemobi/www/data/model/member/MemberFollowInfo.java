package com.bluemobi.www.data.model.member;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * member_follow_info实体表()
 * @author sundq
 * @date 2016-03-02 13:15:05
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberFollowInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 用户ID
	private java.lang.String followMemberId; // 被关注用户ID
	private java.lang.String createDate; // 创建时间
	
	private MemberInfo memberInfo;
	private MemberInfo followMemberInfo;
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
     * 获取招募ID属性
     *
     * @return followMemberId
     */
	public java.lang.String getFollowMemberId() {
		return followMemberId;
	}
	
	/**
	 * 设置招募ID属性
	 *
	 * @param followMemberId
	 */
	public void setFollowMemberId(java.lang.String followMemberId) {
		this.followMemberId = followMemberId;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberFollowInfo");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", followMemberId=").append(followMemberId);
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

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public MemberInfo getFollowMemberInfo() {
		return followMemberInfo;
	}

	public void setFollowMemberInfo(MemberInfo followMemberInfo) {
		this.followMemberInfo = followMemberInfo;
	}
    
}