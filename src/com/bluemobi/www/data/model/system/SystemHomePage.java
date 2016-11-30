package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;

/**
 * system_home_page实体表()
 * @author sundq
 * @date 2016-02-15 13:31:55
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemHomePage extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String imgUuid; // 图片
	private java.lang.String title; // 标题
	private java.lang.Integer orderList; // 排序
	private java.lang.String recruitId; // 招募ID
	private java.lang.String activityId; // 活动ID
	private java.lang.String companyActivityId; // 内部活动ID
	private java.lang.String type; // 类型1-广告、2-招聘推荐、3-急聘信息、4-飙戏视频、5-公司内部活动
	private java.lang.String status; // 状态
	private java.lang.String createDate;//创建时间
	
	private SystemPictureInfo pictureInfo = new SystemPictureInfo();//图片
	private RecruitInfo recruitInfo;//招募
	private ActivityInfo activityInfo;//活动飙戏
	private ActivityCompanyInternal companyInternal;//公司内部活动
	
	private String homeStr;
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
     * 获取排序属性
     *
     * @return orderList
     */
	public java.lang.Integer getOrderList() {
		return orderList;
	}
	
	/**
	 * 设置排序属性
	 *
	 * @param orderList
	 */
	public void setOrderList(java.lang.Integer orderList) {
		this.orderList = orderList;
	}
	
	/**
     * 获取招募ID属性
     *
     * @return recruitId
     */
	public java.lang.String getRecruitId() {
		return recruitId;
	}
	
	/**
	 * 设置招募ID属性
	 *
	 * @param recruitId
	 */
	public void setRecruitId(java.lang.String recruitId) {
		this.recruitId = recruitId;
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
     * 获取内部活动ID属性
     *
     * @return companyActivityId
     */
	public java.lang.String getCompanyActivityId() {
		return companyActivityId;
	}
	
	/**
	 * 设置内部活动ID属性
	 *
	 * @param companyActivityId
	 */
	public void setCompanyActivityId(java.lang.String companyActivityId) {
		this.companyActivityId = companyActivityId;
	}
	
	/**
     * 获取类型属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
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
        sb.append("SystemHomePage");
        sb.append("{id=").append(id);
        sb.append(", imgUuid=").append(imgUuid);
        sb.append(", title=").append(title);
        sb.append(", orderList=").append(orderList);
        sb.append(", recruitId=").append(recruitId);
        sb.append(", activityId=").append(activityId);
        sb.append(", companyActivityId=").append(companyActivityId);
        sb.append(", type=").append(type);
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

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public ActivityInfo getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(ActivityInfo activityInfo) {
		this.activityInfo = activityInfo;
	}

	public ActivityCompanyInternal getCompanyInternal() {
		return companyInternal;
	}

	public void setCompanyInternal(ActivityCompanyInternal companyInternal) {
		this.companyInternal = companyInternal;
	}

	public String getHomeStr() {
		return homeStr;
	}

	public void setHomeStr(String homeStr) {
		this.homeStr = homeStr;
	}
    
}