package com.bluemobi.www.data.model.member;

import java.io.Serializable;

import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * member_resume_apply实体表()
 * @author sundq
 * @date 2016-02-22 09:46:38
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberResumeApply extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 用户ID
	private java.lang.String realName; // 真实姓名
	private java.lang.String sex; // 性别
	private java.lang.String height; // 身高
	private java.lang.String weight; // 体重
	private java.lang.String school; // 毕业院校
	private java.lang.String cityCode; // 经常出现的城市
	private java.lang.String roleLabel; // 角色标签
	private java.lang.String skillLabel; // 技能标签
	private java.lang.String imgUuid; // 头像uuid
	private java.lang.Integer status; // 认证状态
	private java.lang.String imgUuid1; // 
	private java.lang.String imgUuid2; // 
	private java.lang.String imgUuid3; // 
	private java.lang.String imgUuid4; // 
	private java.lang.String imgUuid5; // 
	private java.lang.String imgUuid6; // 
	private java.lang.String videoUuid; // 自我介绍UUID
	private java.lang.String createDate; // 
	private java.lang.String updateDate; // 
	private java.lang.String chest; // 胸围
	private java.lang.String waist; // 腰围
	private java.lang.String hipline; // 臀围
	private java.lang.String birthday; // 生日
	private java.lang.String birthdayStr;// 生日String
	private java.lang.String memberName;//名称
	private java.lang.String roleLabelName;//角色标签名称
	private java.lang.String skillLabelName;//技能标签名称
	private java.lang.String cityName;//城市名称
	
	private MemberInfo memberInfo;//用户信息
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();//头像
	private SystemPictureInfo pictureInfo1 = new SystemPictureInfo();// 正面
	private SystemPictureInfo pictureInfo2 = new SystemPictureInfo();// 左侧
	private SystemPictureInfo pictureInfo3 = new SystemPictureInfo();// 做45度
	private SystemPictureInfo pictureInfo4 = new SystemPictureInfo();// 右侧
	private SystemPictureInfo pictureInfo5 = new SystemPictureInfo();// 右45度
	private SystemPictureInfo pictureInfo6 = new SystemPictureInfo();// 全身照
	private SystemFileInfo fileInfo = new SystemFileInfo();//自我介绍
	
	private String applyId;//申请ID
	private String ids;
	private String applyIds;
	private String reply;
	
	private Double completion;
	private String showStatus;
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
     * 获取真实姓名属性
     *
     * @return realName
     */
	public java.lang.String getRealName() {
		return realName;
	}
	
	/**
	 * 设置真实姓名属性
	 *
	 * @param realName
	 */
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
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
     * 获取身高属性
     *
     * @return height
     */
	public java.lang.String getHeight() {
		return height;
	}
	
	/**
	 * 设置身高属性
	 *
	 * @param height
	 */
	public void setHeight(java.lang.String height) {
		this.height = height;
	}
	
	/**
     * 获取体重属性
     *
     * @return weight
     */
	public java.lang.String getWeight() {
		return weight;
	}
	
	/**
	 * 设置体重属性
	 *
	 * @param weight
	 */
	public void setWeight(java.lang.String weight) {
		this.weight = weight;
	}
	
	/**
     * 获取毕业院校属性
     *
     * @return school
     */
	public java.lang.String getSchool() {
		return school;
	}
	
	/**
	 * 设置毕业院校属性
	 *
	 * @param school
	 */
	public void setSchool(java.lang.String school) {
		this.school = school;
	}
	
	/**
     * 获取经常出现的城市属性
     *
     * @return cityCode
     */
	public java.lang.String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置经常出现的城市属性
	 *
	 * @param cityCode
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
     * 获取角色标签属性
     *
     * @return roleLabel
     */
	public java.lang.String getRoleLabel() {
		return roleLabel;
	}
	
	/**
	 * 设置角色标签属性
	 *
	 * @param roleLabel
	 */
	public void setRoleLabel(java.lang.String roleLabel) {
		this.roleLabel = roleLabel;
	}
	
	/**
     * 获取技能标签属性
     *
     * @return skillLabel
     */
	public java.lang.String getSkillLabel() {
		return skillLabel;
	}
	
	/**
	 * 设置技能标签属性
	 *
	 * @param skillLabel
	 */
	public void setSkillLabel(java.lang.String skillLabel) {
		this.skillLabel = skillLabel;
	}
	
	/**
     * 获取头像uuid属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置头像uuid属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid1
     */
	public java.lang.String getImgUuid1() {
		return imgUuid1;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid1
	 */
	public void setImgUuid1(java.lang.String imgUuid1) {
		this.imgUuid1 = imgUuid1;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid2
     */
	public java.lang.String getImgUuid2() {
		return imgUuid2;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid2
	 */
	public void setImgUuid2(java.lang.String imgUuid2) {
		this.imgUuid2 = imgUuid2;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid3
     */
	public java.lang.String getImgUuid3() {
		return imgUuid3;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid3
	 */
	public void setImgUuid3(java.lang.String imgUuid3) {
		this.imgUuid3 = imgUuid3;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid4
     */
	public java.lang.String getImgUuid4() {
		return imgUuid4;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid4
	 */
	public void setImgUuid4(java.lang.String imgUuid4) {
		this.imgUuid4 = imgUuid4;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid5
     */
	public java.lang.String getImgUuid5() {
		return imgUuid5;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid5
	 */
	public void setImgUuid5(java.lang.String imgUuid5) {
		this.imgUuid5 = imgUuid5;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid6
     */
	public java.lang.String getImgUuid6() {
		return imgUuid6;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid6
	 */
	public void setImgUuid6(java.lang.String imgUuid6) {
		this.imgUuid6 = imgUuid6;
	}
	
	/**
     * 获取自我介绍UUID属性
     *
     * @return videoUuid
     */
	public java.lang.String getVideoUuid() {
		return videoUuid;
	}
	
	/**
	 * 设置自我介绍UUID属性
	 *
	 * @param videoUuid
	 */
	public void setVideoUuid(java.lang.String videoUuid) {
		this.videoUuid = videoUuid;
	}
	
	/**
     * 获取胸围属性
     *
     * @return chest
     */
	public java.lang.String getChest() {
		return chest;
	}
	
	/**
	 * 设置胸围属性
	 *
	 * @param chest
	 */
	public void setChest(java.lang.String chest) {
		this.chest = chest;
	}
	
	/**
     * 获取腰围属性
     *
     * @return waist
     */
	public java.lang.String getWaist() {
		return waist;
	}
	
	/**
	 * 设置腰围属性
	 *
	 * @param waist
	 */
	public void setWaist(java.lang.String waist) {
		this.waist = waist;
	}
	
	/**
     * 获取臀围属性
     *
     * @return hipline
     */
	public java.lang.String getHipline() {
		return hipline;
	}
	
	/**
	 * 设置臀围属性
	 *
	 * @param hipline
	 */
	public void setHipline(java.lang.String hipline) {
		this.hipline = hipline;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberResumeApply");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", realName=").append(realName);
        sb.append(", sex=").append(sex);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", school=").append(school);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", roleLabel=").append(roleLabel);
        sb.append(", skillLabel=").append(skillLabel);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", status=").append(status);
        sb.append(", imgUuid1=").append(imgUuid1);
        sb.append(", imgUuid2=").append(imgUuid2);
        sb.append(", imgUuid3=").append(imgUuid3);
        sb.append(", imgUuid4=").append(imgUuid4);
        sb.append(", imgUuid5=").append(imgUuid5);
        sb.append(", imgUuid6=").append(imgUuid6);
        sb.append(", videoUuid=").append(videoUuid);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", chest=").append(chest);
        sb.append(", waist=").append(waist);
        sb.append(", hipline=").append(hipline);
        sb.append(", birthday=").append(birthday);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.lang.String updateDate) {
		this.updateDate = updateDate;
	}

	public java.lang.String getBirthday() {
		return birthday;
	}

	public void setBirthday(java.lang.String birthday) {
		this.birthday = birthday;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}

	public SystemPictureInfo getPictureInfo1() {
		return pictureInfo1;
	}

	public void setPictureInfo1(SystemPictureInfo pictureInfo1) {
		this.pictureInfo1 = pictureInfo1;
	}

	public SystemPictureInfo getPictureInfo2() {
		return pictureInfo2;
	}

	public void setPictureInfo2(SystemPictureInfo pictureInfo2) {
		this.pictureInfo2 = pictureInfo2;
	}

	public SystemPictureInfo getPictureInfo3() {
		return pictureInfo3;
	}

	public void setPictureInfo3(SystemPictureInfo pictureInfo3) {
		this.pictureInfo3 = pictureInfo3;
	}

	public SystemPictureInfo getPictureInfo4() {
		return pictureInfo4;
	}

	public void setPictureInfo4(SystemPictureInfo pictureInfo4) {
		this.pictureInfo4 = pictureInfo4;
	}

	public SystemPictureInfo getPictureInfo5() {
		return pictureInfo5;
	}

	public void setPictureInfo5(SystemPictureInfo pictureInfo5) {
		this.pictureInfo5 = pictureInfo5;
	}

	public SystemPictureInfo getPictureInfo6() {
		return pictureInfo6;
	}

	public void setPictureInfo6(SystemPictureInfo pictureInfo6) {
		this.pictureInfo6 = pictureInfo6;
	}

	public SystemFileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(SystemFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public java.lang.String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(java.lang.String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public java.lang.String getMemberName() {
		return memberName;
	}

	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}

	public java.lang.String getRoleLabelName() {
		return roleLabelName;
	}

	public void setRoleLabelName(java.lang.String roleLabelName) {
		this.roleLabelName = roleLabelName;
	}

	public java.lang.String getSkillLabelName() {
		return skillLabelName;
	}

	public void setSkillLabelName(java.lang.String skillLabelName) {
		this.skillLabelName = skillLabelName;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getApplyIds() {
		return applyIds;
	}

	public void setApplyIds(String applyIds) {
		this.applyIds = applyIds;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Double getCompletion() {
		return completion;
	}

	public void setCompletion(Double completion) {
		this.completion = completion;
	}

	public String getShowStatus() {
		return showStatus;
	}
	
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}    		
}