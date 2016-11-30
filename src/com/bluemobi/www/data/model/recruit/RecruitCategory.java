package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * recruit_category实体表()
 * @author sundq
 * @date 2016-02-16 11:25:55
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitCategory extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String name; // 
	private java.lang.String status; // 
	private java.lang.String createDate; // 
	private java.lang.Integer orderList; // 
	private java.lang.String color; // 
	
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
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
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
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RecruitCategory");
        sb.append("{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
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

	public java.lang.Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(java.lang.Integer orderList) {
		this.orderList = orderList;
	}

	public java.lang.String getColor() {
		return color;
	}

	public void setColor(java.lang.String color) {
		this.color = color;
	}
    
}