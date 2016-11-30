package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.bluemobi.www.data.model.BaseEntity;

/**
 * discover_info实体表()
 * @author lip
 * @date 2016-08-10 23:21:27
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class DiscoverInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键id
	private java.lang.String type; // 类型
	private java.lang.String cover; // 封面
	private java.lang.String businessId; // 业务id
	private java.lang.Integer orderList; // 排序号
	private java.lang.String status; // 状态
	private java.util.Date createTime; // 创建时间
	private String title;
	
	private String createTimeStr;
	
	/**
     * 获取主键id属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键id属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
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
     * 获取封面属性
     *
     * @return cover
     */
	public java.lang.String getCover() {
		return cover;
	}
	
	/**
	 * 设置封面属性
	 *
	 * @param cover
	 */
	public void setCover(java.lang.String cover) {
		this.cover = cover;
	}
	
	/**
     * 获取业务id属性
     *
     * @return businessId
     */
	public java.lang.String getBusinessId() {
		return businessId;
	}
	
	/**
	 * 设置业务id属性
	 *
	 * @param businessId
	 */
	public void setBusinessId(java.lang.String businessId) {
		this.businessId = businessId;
	}
	
	/**
     * 获取排序号属性
     *
     * @return orderList
     */
	public java.lang.Integer getOrderList() {
		return orderList;
	}
	
	/**
	 * 设置排序号属性
	 *
	 * @param orderList
	 */
	public void setOrderList(java.lang.Integer orderList) {
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
	
	/**
     * 获取创建时间属性
     *
     * @return createTime
     */
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置创建时间属性
	 *
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}		
	
	public String getCreateTimeStr() {
		if(createTime != null){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
		}
		return createTimeStr;
	}
	
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
			
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DiscoverInfo");
        sb.append("{id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", cover=").append(cover);
        sb.append(", businessId=").append(businessId);
        sb.append(", orderList=").append(orderList);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
		sb.append('}');
        return sb.toString();
    }
    
}