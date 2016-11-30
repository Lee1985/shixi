package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_lable_info实体表()
 * @author sundq
 * @date 2016-02-02 13:45:35
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemLableInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String lableName; // 标签名称
	private java.lang.String lableType; // 标签类型1角色 2 技能
	private java.lang.String lableContent; // 标签描述
	private java.lang.String status; // 状态
	private java.lang.String createDate; // 创建时间
	private java.lang.String createUser; // 
	private java.lang.String typeId;//类型ID
	private java.lang.String memberId;//所属用户Id
	
	private SystemLableType systemLableType;
	
	private String group;
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

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
     * 获取标签名称属性
     *
     * @return lableName
     */
	public java.lang.String getLableName() {
		return lableName;
	}
	
	/**
	 * 设置标签名称属性
	 *
	 * @param lableName
	 */
	public void setLableName(java.lang.String lableName) {
		this.lableName = lableName;
	}
	
	/**
     * 获取标签类型1角色 2 技能属性
     *
     * @return lableType
     */
	public java.lang.String getLableType() {
		return lableType;
	}
	
	/**
	 * 设置标签类型1角色 2 技能属性
	 *
	 * @param lableType
	 */
	public void setLableType(java.lang.String lableType) {
		this.lableType = lableType;
	}
	
	/**
     * 获取标签描述属性
     *
     * @return lableContent
     */
	public java.lang.String getLableContent() {
		return lableContent;
	}
	
	/**
	 * 设置标签描述属性
	 *
	 * @param lableContent
	 */
	public void setLableContent(java.lang.String lableContent) {
		this.lableContent = lableContent;
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
	
	
	/**
     * 获取属性
     *
     * @return createUser
     */
	public java.lang.String getCreateUser() {
		return createUser;
	}
	
	/**
	 * 设置属性
	 *
	 * @param createUser
	 */
	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemLableInfo");
        sb.append("{id=").append(id);
        sb.append(", lableName=").append(lableName);
        sb.append(", lableType=").append(lableType);
        sb.append(", lableContent=").append(lableContent);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.String typeId) {
		this.typeId = typeId;
	}

	public SystemLableType getSystemLableType() {
		return systemLableType;
	}

	public void setSystemLableType(SystemLableType systemLableType) {
		this.systemLableType = systemLableType;
	}

	public java.lang.String getMemberId() {
		return memberId;
	}

	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}
    
}