package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_advertisement_info实体表()
 * @author sundq
 * @date 2016-02-04 13:11:33
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemAdvertisementInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 
	private java.lang.String imgUuid; // 
	private java.lang.Integer orderList; // 
	private java.lang.String status; // 
	private java.lang.String createDate; // 
	private java.lang.String content; // 内容
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();
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
     * @return title
     */
	public java.lang.String getTitle() {
		return title;
	}
	
	/**
	 * 设置属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取属性
     *
     * @return imgUuid
     */
	public java.lang.String getImgUuid() {
		return imgUuid;
	}
	
	/**
	 * 设置属性
	 *
	 * @param imgUuid
	 */
	public void setImgUuid(java.lang.String imgUuid) {
		this.imgUuid = imgUuid;
	}
	
	/**
     * 获取属性
     *
     * @return orderList
     */
	public java.lang.Integer getOrderList() {
		return orderList;
	}
	
	/**
	 * 设置属性
	 *
	 * @param orderList
	 */
	public void setOrderList(java.lang.Integer orderList) {
		this.orderList = orderList;
	}
	
	/**
     * 获取属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
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
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemAdvertisementInfo");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", orderList=").append(orderList);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", content=").append(content);
		sb.append('}');
        return sb.toString();
    }


	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}
    
}