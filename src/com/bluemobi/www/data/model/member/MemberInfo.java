package com.bluemobi.www.data.model.member;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * member_info实体表()
 * @author sundq
 * @date 2016-02-04 16:47:31
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String mobile; // 手机
	private java.lang.String email; // 邮箱
	private java.lang.String password; // 密码
	private java.lang.String imgUuid; // 头像
	private java.lang.String nickname; // 昵称
	private java.lang.String sex; // 性别
	private java.lang.String cityCode; // 城市编码
	private java.lang.String realName; // 真实姓名
	private java.lang.String identityStatus; // 身份认证 0未提交 1通过 2拒绝 3待审核
	private java.lang.String realNameStatus; // 实名认证状态 0未提交 1通过 2拒绝 3待审核
	private java.lang.String educationStatus; // 学历认证状态 0未提交 1通过 2拒绝 3待审核
	private java.lang.String createDate; // 创建时间
	private java.lang.String updateDate; // 修改时间
	private java.lang.String status; // 状态
	private java.lang.String identityInfo; // 
	private java.lang.String idCardUuidA; // 身份证正面
	private java.lang.String idCardUuidB; // 身份证反面
	private java.lang.String degreeImgUuid; // 学位证
	private java.lang.String diplomaImgUuid; // 毕业证照片
	private java.lang.String cid;//设备ID
	private java.lang.String IDcard;//身份证号
	private java.lang.String vip;//会员
	private java.lang.String school;//毕业院校
	
	private java.lang.String ids;//
	private java.lang.String reply;//回复
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();// 头像
	private SystemPictureInfo idCardpictureInfoA = new SystemPictureInfo();// 身份证正面
	private SystemPictureInfo idCardpictureInfoB = new SystemPictureInfo();// 身份证反面
	private SystemPictureInfo degreepictureInfo = new SystemPictureInfo();// 学位证
	private SystemPictureInfo diplomapictureInfo = new SystemPictureInfo();// 毕业证照片
	
	private String headImageUrl;
	private String type;//认证 1-实名  2-学历
	
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
     * 获取邮箱属性
     *
     * @return email
     */
	public java.lang.String getEmail() {
		return email;
	}
	
	/**
	 * 设置邮箱属性
	 *
	 * @param email
	 */
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	/**
     * 获取密码属性
     *
     * @return password
     */
	public java.lang.String getPassword() {
		return password;
	}
	
	/**
	 * 设置密码属性
	 *
	 * @param password
	 */
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	/**
     * 获取头像属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置头像属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取昵称属性
     *
     * @return nickname
     */
	public java.lang.String getNickname() {
		return nickname;
	}
	
	/**
	 * 设置昵称属性
	 *
	 * @param nickname
	 */
	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
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
     * 获取城市编码属性
     *
     * @return cityCode
     */
	public java.lang.String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置城市编码属性
	 *
	 * @param cityCode
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
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
     * 获取身份认证 0未提交 1通过 2拒绝 3审核中属性
     *
     * @return identityStatus
     */
	public java.lang.String getIdentityStatus() {
		return identityStatus;
	}
	
	/**
	 * 设置身份认证 0未提交 1通过 2拒绝 3审核中属性
	 *
	 * @param identityStatus
	 */
	public void setIdentityStatus(java.lang.String identityStatus) {
		this.identityStatus = identityStatus;
	}
	
	/**
     * 获取实名认证状态 0未提交 1通过 2拒绝 3审核中属性
     *
     * @return realNameStatus
     */
	public java.lang.String getRealNameStatus() {
		return realNameStatus;
	}
	
	/**
	 * 设置实名认证状态 0未提交 1通过 2拒绝 3审核中属性
	 *
	 * @param realNameStatus
	 */
	public void setRealNameStatus(java.lang.String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	
	/**
     * 获取学历认证状态 0未提交 1通过 2拒绝 3审核中属性
     *
     * @return educationStatus
     */
	public java.lang.String getEducationStatus() {
		return educationStatus;
	}
	
	/**
	 * 设置学历认证状态 0未提交 1通过 2拒绝 3审核中属性
	 *
	 * @param educationStatus
	 */
	public void setEducationStatus(java.lang.String educationStatus) {
		this.educationStatus = educationStatus;
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
     * @return identityInfo
     */
	public java.lang.String getIdentityInfo() {
		return identityInfo;
	}
	
	/**
	 * 设置属性
	 *
	 * @param identityInfo
	 */
	public void setIdentityInfo(java.lang.String identityInfo) {
		this.identityInfo = identityInfo;
	}
	
	/**
     * 获取身份证正面属性
     *
     * @return idCardUuidA
     */
	public java.lang.String getIdCardUuidA() {
		return idCardUuidA;
	}
	
	/**
	 * 设置身份证正面属性
	 *
	 * @param idCardUuidA
	 */
	public void setIdCardUuidA(java.lang.String idCardUuidA) {
		this.idCardUuidA = idCardUuidA;
	}
	
	/**
     * 获取身份证反面属性
     *
     * @return idCardUuidB
     */
	public java.lang.String getIdCardUuidB() {
		return idCardUuidB;
	}
	
	/**
	 * 设置身份证反面属性
	 *
	 * @param idCardUuidB
	 */
	public void setIdCardUuidB(java.lang.String idCardUuidB) {
		this.idCardUuidB = idCardUuidB;
	}
	
	/**
     * 获取学位证属性
     *
     * @return degreeImgUuid
     */
	public java.lang.String getDegreeImgUuid() {
		return degreeImgUuid;
	}
	
	/**
	 * 设置学位证属性
	 *
	 * @param degreeImgUuid
	 */
	public void setDegreeImgUuid(java.lang.String degreeImgUuid) {
		this.degreeImgUuid = degreeImgUuid;
	}
	
	/**
     * 获取毕业证照片属性
     *
     * @return diplomaImgUuid
     */
	public java.lang.String getDiplomaImgUuid() {
		return diplomaImgUuid;
	}
	
	/**
	 * 设置毕业证照片属性
	 *
	 * @param diplomaImgUuid
	 */
	public void setDiplomaImgUuid(java.lang.String diplomaImgUuid) {
		this.diplomaImgUuid = diplomaImgUuid;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberInfo");
        sb.append("{id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", nickname=").append(nickname);
        sb.append(", sex=").append(sex);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", realName=").append(realName);
        sb.append(", identityStatus=").append(identityStatus);
        sb.append(", realNameStatus=").append(realNameStatus);
        sb.append(", educationStatus=").append(educationStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", status=").append(status);
        sb.append(", identityInfo=").append(identityInfo);
        sb.append(", idCardUuidA=").append(idCardUuidA);
        sb.append(", idCardUuidB=").append(idCardUuidB);
        sb.append(", degreeImgUuid=").append(degreeImgUuid);
        sb.append(", diplomaImgUuid=").append(diplomaImgUuid);
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

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}

	public SystemPictureInfo getIdCardpictureInfoA() {
		return idCardpictureInfoA;
	}

	public void setIdCardpictureInfoA(SystemPictureInfo idCardpictureInfoA) {
		this.idCardpictureInfoA = idCardpictureInfoA;
	}

	public SystemPictureInfo getIdCardpictureInfoB() {
		return idCardpictureInfoB;
	}

	public void setIdCardpictureInfoB(SystemPictureInfo idCardpictureInfoB) {
		this.idCardpictureInfoB = idCardpictureInfoB;
	}

	public SystemPictureInfo getDegreepictureInfo() {
		return degreepictureInfo;
	}

	public void setDegreepictureInfo(SystemPictureInfo degreepictureInfo) {
		this.degreepictureInfo = degreepictureInfo;
	}

	public SystemPictureInfo getDiplomapictureInfo() {
		return diplomapictureInfo;
	}

	public void setDiplomapictureInfo(SystemPictureInfo diplomapictureInfo) {
		this.diplomapictureInfo = diplomapictureInfo;
	}

	public java.lang.String getCid() {
		return cid;
	}

	public void setCid(java.lang.String cid) {
		this.cid = cid;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public java.lang.String getIDcard() {
		return IDcard;
	}

	public void setIDcard(java.lang.String iDcard) {
		IDcard = iDcard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.lang.String getIds() {
		return ids;
	}

	public void setIds(java.lang.String ids) {
		this.ids = ids;
	}

	public java.lang.String getReply() {
		return reply;
	}

	public void setReply(java.lang.String reply) {
		this.reply = reply;
	}

	public java.lang.String getVip() {
		return vip;
	}

	public void setVip(java.lang.String vip) {
		this.vip = vip;
	}

	public java.lang.String getSchool() {
		return school;
	}

	public void setSchool(java.lang.String school) {
		this.school = school;
	}		
    
}