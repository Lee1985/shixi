package com.bluemobi.www.data.model.activity;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * activity_video_like实体表()
 * @author lip
 * @date 2016-03-15 16:29:37
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ActivityVideoLike extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String memberId; // 
	private java.lang.String videoInfoId; // 
	private java.lang.String createDate; // 
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
     * @return videoInfoId
     */
	public java.lang.String getVideoInfoId() {
		return videoInfoId;
	}
	
	/**
	 * 设置属性
	 *
	 * @param videoInfoId
	 */
	public void setVideoInfoId(java.lang.String videoInfoId) {
		this.videoInfoId = videoInfoId;
	}
		
	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActivityVideoLike");
        sb.append("{id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", videoInfoId=").append(videoInfoId);
        sb.append(", createDate=").append(createDate);
		sb.append('}');
        return sb.toString();
    }
    
}