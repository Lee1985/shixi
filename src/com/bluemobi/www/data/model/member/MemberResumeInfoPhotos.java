package com.bluemobi.www.data.model.member;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;

/**
 * member_resume_info_photos实体表()
 * @author sundq
 * @date 2016-02-26 13:00:23
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class MemberResumeInfoPhotos extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String resumeId; // 简历ID
	private java.lang.String imgUuid1; // 照片1
	private java.lang.String imgUuid2; // 照片2
	private java.lang.String imgUuid3; // 照片3
	private java.lang.String imgUuid4; // 照片4
	private java.lang.String imgUuid5; // 照片5
	private java.lang.String imgUuidAll; // 照片整体
	private java.lang.String width; // 宽
	private java.lang.String height; // 高
	private java.lang.String createDate; // 创建时间

	private SystemPictureInfo pictureInfo1;// 照片1
	private SystemPictureInfo pictureInfo2;// 照片2
	private SystemPictureInfo pictureInfo3;// 照片3
	private SystemPictureInfo pictureInfo4;// 照片4
	private SystemPictureInfo pictureInfo5;// 照片5
	private SystemPictureInfo pictureInfoAll;// 全身照
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
     * 获取照片1属性
     *
     * @return imgUuid1
     */
	public java.lang.String getImgUuid1() {
		return imgUuid1;
	}
	
	/**
	 * 设置照片1属性
	 *
	 * @param imgUuid1
	 */
	public void setImgUuid1(java.lang.String imgUuid1) {
		this.imgUuid1 = imgUuid1;
	}
	
	/**
     * 获取照片2属性
     *
     * @return imgUuid2
     */
	public java.lang.String getImgUuid2() {
		return imgUuid2;
	}
	
	/**
	 * 设置照片2属性
	 *
	 * @param imgUuid2
	 */
	public void setImgUuid2(java.lang.String imgUuid2) {
		this.imgUuid2 = imgUuid2;
	}
	
	/**
     * 获取照片3属性
     *
     * @return imgUuid3
     */
	public java.lang.String getImgUuid3() {
		return imgUuid3;
	}
	
	/**
	 * 设置照片3属性
	 *
	 * @param imgUuid3
	 */
	public void setImgUuid3(java.lang.String imgUuid3) {
		this.imgUuid3 = imgUuid3;
	}
	
	/**
     * 获取照片4属性
     *
     * @return imgUuid4
     */
	public java.lang.String getImgUuid4() {
		return imgUuid4;
	}
	
	/**
	 * 设置照片4属性
	 *
	 * @param imgUuid4
	 */
	public void setImgUuid4(java.lang.String imgUuid4) {
		this.imgUuid4 = imgUuid4;
	}
	
	/**
     * 获取照片5属性
     *
     * @return imgUuid5
     */
	public java.lang.String getImgUuid5() {
		return imgUuid5;
	}
	
	/**
	 * 设置照片5属性
	 *
	 * @param imgUuid5
	 */
	public void setImgUuid5(java.lang.String imgUuid5) {
		this.imgUuid5 = imgUuid5;
	}
	
	/**
     * 获取照片整体属性
     *
     * @return imgUuidAll
     */
	public java.lang.String getImgUuidAll() {
		return imgUuidAll;
	}
	
	/**
	 * 设置照片整体属性
	 *
	 * @param imgUuidAll
	 */
	public void setImgUuidAll(java.lang.String imgUuidAll) {
		this.imgUuidAll = imgUuidAll;
	}
	
	/**
     * 获取宽属性
     *
     * @return width
     */
	public java.lang.String getWidth() {
		return width;
	}
	
	/**
	 * 设置宽属性
	 *
	 * @param width
	 */
	public void setWidth(java.lang.String width) {
		this.width = width;
	}
	
	/**
     * 获取高属性
     *
     * @return height
     */
	public java.lang.String getHeight() {
		return height;
	}
	
	/**
	 * 设置高属性
	 *
	 * @param height
	 */
	public void setHeight(java.lang.String height) {
		this.height = height;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberResumeInfoPhotos");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", resumeId=").append(resumeId);
        sb.append(", imgUuid1=").append(imgUuid1);
        sb.append(", imgUuid2=").append(imgUuid2);
        sb.append(", imgUuid3=").append(imgUuid3);
        sb.append(", imgUuid4=").append(imgUuid4);
        sb.append(", imgUuid5=").append(imgUuid5);
        sb.append(", imgUuidAll=").append(imgUuidAll);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
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

	public SystemPictureInfo getPictureInfoAll() {
		return pictureInfoAll;
	}

	public void setPictureInfoAll(SystemPictureInfo pictureInfoAll) {
		this.pictureInfoAll = pictureInfoAll;
	}
    
}