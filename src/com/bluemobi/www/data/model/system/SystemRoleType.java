package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_role_type实体表()
 * @author sundq
 * @date 2016-02-24 14:07:03
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemRoleType extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String typeName; // 类别名称
	private java.lang.String status; // 状态
	private java.lang.Integer orderList; // 排序
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
     * 获取类别名称属性
     *
     * @return typeName
     */
	public java.lang.String getTypeName() {
		return typeName;
	}
	
	/**
	 * 设置类别名称属性
	 *
	 * @param typeName
	 */
	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
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
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemRoleType");
        sb.append("{id=").append(id);
        sb.append(", typeName=").append(typeName);
        sb.append(", status=").append(status);
        sb.append(", orderList=").append(orderList);
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