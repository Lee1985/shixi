package com.bluemobi.www.data.model.activity;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;

/**
 * activity_video_comment实体表()
 * @author sundq
 * @date 2016-02-14 14:18:20
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ActivityVideoComment extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 用户ID
	private java.lang.String videoInfoId; // 活动视频ID
	private java.lang.String content; // 评论内容
	private java.lang.String createDate; // 创建时间
	
	private MemberInfo memberInfo = new MemberInfo();//用户
	private ActivityVideoInfo activityVideoInfo;//活动视频
	
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
     * 获取活动视频ID属性
     *
     * @return videoInfoId
     */
	public java.lang.String getVideoInfoId() {
		return videoInfoId;
	}
	
	/**
	 * 设置活动视频ID属性
	 *
	 * @param videoInfoId
	 */
	public void setVideoInfoId(java.lang.String videoInfoId) {
		this.videoInfoId = videoInfoId;
	}
	
	/**
     * 获取评论内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置评论内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActivityVideoComment");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", videoInfoId=").append(videoInfoId);
        sb.append(", content=").append(content);
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

	public ActivityVideoInfo getActivityVideoInfo() {
		return activityVideoInfo;
	}

	public void setActivityVideoInfo(ActivityVideoInfo activityVideoInfo) {
		this.activityVideoInfo = activityVideoInfo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
    
}