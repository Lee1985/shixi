package com.bluemobi.www.data.model.activity;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;

/**
 * activity_video_info实体表()
 * @author sundq
 * @date 2016-02-14 14:16:40
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ActivityVideoInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String activityId; // 活动ID
	private java.lang.String memberId; // 用户ID
	private java.lang.String videoId; // 视频ID
	private java.lang.Integer viewNum; // 浏览量
	private java.lang.Integer shareNum; // 分享次数
	private java.lang.Integer likeNum; // 点赞数
	private java.lang.Integer commentNum; // 评论数
	private java.lang.String createDate; // 创建时间
	private java.lang.String status; // 状态
	private java.lang.String isHot; // 是否热点
	private String phoneType;
	
	private ActivityInfo activityInfo;
	private MemberInfo memberInfo;
	private SystemFileInfo fileInfo;
	
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
     * 获取活动ID属性
     *
     * @return activityId
     */
	public java.lang.String getActivityId() {
		return activityId;
	}
	
	/**
	 * 设置活动ID属性
	 *
	 * @param activityId
	 */
	public void setActivityId(java.lang.String activityId) {
		this.activityId = activityId;
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
     * 获取视频ID属性
     *
     * @return videoId
     */
	public java.lang.String getVideoId() {
		return videoId;
	}
	
	/**
	 * 设置视频ID属性
	 *
	 * @param videoId
	 */
	public void setVideoId(java.lang.String videoId) {
		this.videoId = videoId;
	}
	
	/**
     * 获取浏览量属性
     *
     * @return viewNum
     */
	public java.lang.Integer getViewNum() {
		return viewNum;
	}
	
	/**
	 * 设置浏览量属性
	 *
	 * @param viewNum
	 */
	public void setViewNum(java.lang.Integer viewNum) {
		this.viewNum = viewNum;
	}
	
	/**
     * 获取分享次数属性
     *
     * @return shareNum
     */
	public java.lang.Integer getShareNum() {
		return shareNum;
	}
	
	/**
	 * 设置分享次数属性
	 *
	 * @param shareNum
	 */
	public void setShareNum(java.lang.Integer shareNum) {
		this.shareNum = shareNum;
	}
	
	/**
     * 获取点赞数属性
     *
     * @return likeNum
     */
	public java.lang.Integer getLikeNum() {
		return likeNum;
	}
	
	/**
	 * 设置点赞数属性
	 *
	 * @param likeNum
	 */
	public void setLikeNum(java.lang.Integer likeNum) {
		this.likeNum = likeNum;
	}
	
	/**
     * 获取评论数属性
     *
     * @return commentNum
     */
	public java.lang.Integer getCommentNum() {
		return commentNum;
	}
	
	/**
	 * 设置评论数属性
	 *
	 * @param commentNum
	 */
	public void setCommentNum(java.lang.Integer commentNum) {
		this.commentNum = commentNum;
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
     * 获取是否热点属性
     *
     * @return isHot
     */
	public java.lang.String getIsHot() {
		return isHot;
	}
	
	/**
	 * 设置是否热点属性
	 *
	 * @param isHot
	 */
	public void setIsHot(java.lang.String isHot) {
		this.isHot = isHot;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActivityVideoInfo");
        sb.append("{id=").append(id);
        sb.append(", activityId=").append(activityId);
        sb.append(", memberId=").append(memberId);
        sb.append(", videoId=").append(videoId);
        sb.append(", viewNum=").append(viewNum);
        sb.append(", shareNum=").append(shareNum);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
        sb.append(", isHot=").append(isHot);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public ActivityInfo getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(ActivityInfo activityInfo) {
		this.activityInfo = activityInfo;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public SystemFileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(SystemFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}    
}