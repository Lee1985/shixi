package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;
import java.util.List;

import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.data.model.system.SystemFileInfo;

/**
 * recruit_apply_info实体表()
 * @author sundq
 * @date 2016-02-23 09:01:13
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitApplyInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String roleId; // 角色ID
	private java.lang.String memberId; // 用户ID
	private java.lang.String resumeId; // 简历ID
	private java.lang.String videoUuid; // 视频ID
	private java.lang.String createDate; // 创建时间
	private java.lang.String updateDate; // 修改时间
	private java.lang.String status; // 简历审核状态
	private java.lang.String videoStatus; // 视频审核状态
	private java.lang.String level;// 进度 1-简历审核中  2-简历投递成功 3-剧组已查看简历 4-剧组关注了你 5-剧组联系了你
	private java.lang.String optional;//备选
	private double completion;
	private java.lang.String isView;//是否查看
	private java.lang.String isContact;//是否联系
	private java.lang.String isVideo;//看过20视频
	
	private MemberInfo memberInfo;// 用户信息
	private RecruitRoleInfo roleInfo;// 角色
	private SystemFileInfo fileInfo = new SystemFileInfo();// 文件
	private MemberResumeApply resumeApply;// 简历
	
	private String recruitName;//剧组名
	private String roleName;//角色名
	private String publishName;//发布人
	private String inLevel;
	private String realname;
	private String nickname;
	private String identityType;
	private String ids;
	private String reply;
	
	private String resumeTempId;//用户本地简历ID
	
	private List<String> resumeIdList;
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
     * 获取角色ID属性
     *
     * @return roleId
     */
	public java.lang.String getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置角色ID属性
	 *
	 * @param roleId
	 */
	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
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
     * 获取简历ID属性
     *
     * @return resumeId
     */
	public java.lang.String getResumeId() {
		return resumeId;
	}
	
	/**
	 * 设置简历ID属性
	 *
	 * @param resumeId
	 */
	public void setResumeId(java.lang.String resumeId) {
		this.resumeId = resumeId;
	}
	
	/**
     * 获取审核状态属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置审核状态属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RecruitApplyInfo");
        sb.append("{id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", memberId=").append(memberId);
        sb.append(", resumeId=").append(resumeId);
        sb.append(", videoUuid=").append(videoUuid);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", status=").append(status);
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

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public RecruitRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RecruitRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public SystemFileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(SystemFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public java.lang.String getVideoUuid() {
		return videoUuid;
	}

	public void setVideoUuid(java.lang.String videoUuid) {
		this.videoUuid = videoUuid;
	}

	public String getRecruitName() {
		return recruitName;
	}

	public void setRecruitName(String recruitName) {
		this.recruitName = recruitName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public java.lang.String getLevel() {
		return level;
	}

	public void setLevel(java.lang.String level) {
		this.level = level;
	}

	public MemberResumeApply getResumeApply() {
		return resumeApply;
	}

	public void setResumeApply(MemberResumeApply resumeApply) {
		this.resumeApply = resumeApply;
	}

	public java.lang.String getVideoStatus() {
		return videoStatus;
	}

	public void setVideoStatus(java.lang.String videoStatus) {
		this.videoStatus = videoStatus;
	}

	public String getResumeTempId() {
		return resumeTempId;
	}

	public void setResumeTempId(String resumeTempId) {
		this.resumeTempId = resumeTempId;
	}

	public java.lang.String getOptional() {
		return optional;
	}

	public void setOptional(java.lang.String optional) {
		this.optional = optional;
	}

	public String getInLevel() {
		return inLevel;
	}

	public void setInLevel(String inLevel) {
		this.inLevel = inLevel;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public double getCompletion() {
		return completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public java.lang.String getIsView() {
		return isView;
	}

	public void setIsView(java.lang.String isView) {
		this.isView = isView;
	}

	public java.lang.String getIsContact() {
		return isContact;
	}

	public void setIsContact(java.lang.String isContact) {
		this.isContact = isContact;
	}

	public java.lang.String getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(java.lang.String isVideo) {
		this.isVideo = isVideo;
	}

	public List<String> getResumeIdList() {
		return resumeIdList;
	}

	public void setResumeIdList(List<String> resumeIdList) {
		this.resumeIdList = resumeIdList;
	}    	
}