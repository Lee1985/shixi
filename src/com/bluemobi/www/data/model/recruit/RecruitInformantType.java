package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * recruit_informant_type实体表()
 * @author sundq
 * @date 2016-02-18 10:35:46
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitInformantType extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String orderList; // 排序
	private java.lang.String status; // 状态
	private java.lang.String createDate; // 创建时间
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
        sb.append("RecruitInformantType");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", orderList=").append(orderList);
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
    
}