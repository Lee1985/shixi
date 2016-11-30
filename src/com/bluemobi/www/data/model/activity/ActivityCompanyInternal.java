package com.bluemobi.www.data.model.activity;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * activity_company_internal实体表()
 * @author sundq
 * @date 2016-02-15 16:10:06
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ActivityCompanyInternal extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String imgUuid; // 图片
	private java.lang.String content; // 内容
	private java.lang.String createDate; // 创建时间
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();//图片
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
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ActivityCompanyInternal");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", imgUuid=").append(imgUuid);
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

	public SystemPictureInfo getPictureInfo() {
		return pictureInfo;
	}

	public void setPictureInfo(SystemPictureInfo pictureInfo) {
		this.pictureInfo = pictureInfo;
	}
    
}