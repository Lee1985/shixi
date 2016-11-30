package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;

import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * recruit_role_info_temp实体表()
 * @author sundq
 * @date 2016-03-08 13:19:09
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitRoleInfoTemp extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String recruitId; // 招募ID
	private java.lang.String roleId; // 已发布角色ID
	private java.lang.String name; // 角色名称
	private java.lang.String sex; // 性别
	private java.lang.String lableCode; // 标签
	private java.lang.String paidMin; // 最小片酬
	private java.lang.String paidMax; // 最大片酬
	private java.lang.String paidType; // 天   /  周  /  月  /  集  /  场  /  面议
	private java.lang.String requirement; // 导演要求
	private java.lang.String dialogue; // 试戏台词
	private java.lang.String imgUuid; // 角色图片
	private java.lang.String isDelete; // 删除状态(0-未删除,1-已删除)
	private java.lang.String status; // 状态
	private java.lang.String createDate; // 创建时间
	private java.lang.String operType;// 操作类型 1-增  2-改  3-删
	
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();//图片
	private RecruitInfoTemp recruitTempInfo;//招募信息
	private String lableNames; // 标签名称
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
     * 获取招募ID属性
     *
     * @return recruitId
     */
	public java.lang.String getRecruitId() {
		return recruitId;
	}
	
	/**
	 * 设置招募ID属性
	 *
	 * @param recruitId
	 */
	public void setRecruitId(java.lang.String recruitId) {
		this.recruitId = recruitId;
	}
	
	/**
     * 获取角色名称属性
     *
     * @return name
     */
	public java.lang.String getName() {		
		return name;
	}
	
	/**
	 * 设置角色名称属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
     * 获取性别属性
     *
     * @return sex
     */
	public java.lang.String getSex() {
		return sex;
	}
	
	/**
	 * 设置性别属性
	 *
	 * @param sex
	 */
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	
	/**
     * 获取标签属性
     *
     * @return lableCode
     */
	public java.lang.String getLableCode() {
		return lableCode;
	}
	
	/**
	 * 设置标签属性
	 *
	 * @param lableCode
	 */
	public void setLableCode(java.lang.String lableCode) {
		this.lableCode = lableCode;
	}
	
	/**
     * 获取最小片酬属性
     *
     * @return paidMin
     */
	public java.lang.String getPaidMin() {
		return paidMin;
	}
	
	/**
	 * 设置最小片酬属性
	 *
	 * @param paidMin
	 */
	public void setPaidMin(java.lang.String paidMin) {
		this.paidMin = paidMin;
	}
	
	/**
     * 获取最大片酬属性
     *
     * @return paidMax
     */
	public java.lang.String getPaidMax() {
		return paidMax;
	}
	
	/**
	 * 设置最大片酬属性
	 *
	 * @param paidMax
	 */
	public void setPaidMax(java.lang.String paidMax) {
		this.paidMax = paidMax;
	}
	
	/**
     * 获取天   /  周  /  月  /  集  /  场  /  面议属性
     *
     * @return paidType
     */
	public java.lang.String getPaidType() {
		return paidType;
	}
	
	/**
	 * 设置天   /  周  /  月  /  集  /  场  /  面议属性
	 *
	 * @param paidType
	 */
	public void setPaidType(java.lang.String paidType) {
		this.paidType = paidType;
	}
	
	/**
     * 获取导演要求属性
     *
     * @return requirement
     */
	public java.lang.String getRequirement() {		
		return requirement;
	}
	
	/**
	 * 设置导演要求属性
	 *
	 * @param requirement
	 */
	public void setRequirement(java.lang.String requirement) {
		this.requirement = requirement;
	}
	
	/**
     * 获取试戏台词属性
     *
     * @return dialogue
     */
	public java.lang.String getDialogue() {		
		return dialogue;
	}
	
	/**
	 * 设置试戏台词属性
	 *
	 * @param dialogue
	 */
	public void setDialogue(java.lang.String dialogue) {
		this.dialogue = dialogue;
	}
	
	/**
     * 获取角色图片属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置角色图片属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取删除状态(0-未删除,1-已删除)属性
     *
     * @return isDelete
     */
	public java.lang.String getIsDelete() {
		return isDelete;
	}
	
	/**
	 * 设置删除状态(0-未删除,1-已删除)属性
	 *
	 * @param isDelete
	 */
	public void setIsDelete(java.lang.String isDelete) {
		this.isDelete = isDelete;
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
        sb.append("RecruitRoleInfoTemp");
        sb.append("{id=").append(id);
        sb.append(", recruitId=").append(recruitId);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", lableCode=").append(lableCode);
        sb.append(", paidMin=").append(paidMin);
        sb.append(", paidMax=").append(paidMax);
        sb.append(", paidType=").append(paidType);
        sb.append(", requirement=").append(requirement);
        sb.append(", dialogue=").append(dialogue);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
		sb.append('}');
        return sb.toString();
    }

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}

	public String getLableNames() {
		return lableNames;
	}

	public void setLableNames(String lableNames) {
		this.lableNames = lableNames;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public java.lang.String getOperType() {
		return operType;
	}

	public void setOperType(java.lang.String operType) {
		this.operType = operType;
	}

	public java.lang.String getRoleId() {
		return roleId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	public RecruitInfoTemp getRecruitTempInfo() {
		return recruitTempInfo;
	}

	public void setRecruitTempInfo(RecruitInfoTemp recruitTempInfo) {
		this.recruitTempInfo = recruitTempInfo;
	}    
}