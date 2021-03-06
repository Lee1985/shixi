package com.bluemobi.www.data.model.activity;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * activity_info实体表()
 * @author sundq
 * @date 2016-02-14 14:13:11
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ActivityInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String imgUuid; // 图片
	private java.lang.String content; // 内容
	private java.lang.String orderList; // 排序
	private java.lang.String createDate; // 创建时间
	private java.lang.String status; // 状态
	private java.lang.String videoUuid;// 活动视频
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();//图片
	private SystemFileInfo fileInfo = new SystemFileInfo();//视频
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
     * 获取标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取图片属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置图片属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取排序属性
     *
     * @return orderList
     */
	public java.lang.String getOrderList() {
		return orderList;
	}
	
	/**
	 * 设置排序属性
	 *
	 * @param orderList
	 */
	public void setOrderList(java.lang.String orderList) {
		this.orderList = orderList;
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
        sb.append("ActivityInfo");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", content=").append(content);
        sb.append(", orderList=").append(orderList);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getVideoUuid() {
		return videoUuid;
	}

	public void setVideoUuid(java.lang.String videoUuid) {
		this.videoUuid = videoUuid;
	}

	public SystemFileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(SystemFileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}
    
}