package com.bluemobi.www.data.model.member;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;

/**
 * member_actions_info实体表()
 * @author sundq
 * @date 2016-03-10 10:29:21
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberActionsInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 
	private java.lang.String roleId; // 
	private java.lang.String createDate; // 
	
	private RecruitRoleInfo roleInfo;
	private MemberInfo memberInfo;
	
	private String memberName;
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
     * 获取属性
     *
     * @return memberId
     */
	public java.lang.String getMemberId() {
		return memberId;
	}
	
	/**
	 * 设置属性
	 *
	 * @param memberId
	 */
	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}
	
	/**
     * 获取属性
     *
     * @return roleId
     */
	public java.lang.String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置属性
	 *
	 * @param roleId
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberActionsInfo");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", roleId=").append(roleId);
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

	public RecruitRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RecruitRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
    
}