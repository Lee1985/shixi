package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_hotspot_city实体表()
 * @author lip
 * @date 2016-02-18 13:47:12
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemHotspotCity extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String cityName; // 城市名
	private java.lang.Integer orderList; // 排序
	private java.lang.String spell; // 拼音
	private java.lang.String zipcode; // 城市编码
	private java.lang.String status; // 状态
	private java.lang.String createDate; // 
	private java.lang.String createUser; // 
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
     * 获取城市名属性
     *
     * @return cityName
     */
	public java.lang.String getCityName() {
		return cityName;
	}
	
	/**
	 * 设置城市名属性
	 *
	 * @param cityName
	 */
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	/**
     * 获取拼音属性
     *
     * @return spell
     */
	public java.lang.String getSpell() {
		return spell;
	}
	
	/**
	 * 设置拼音属性
	 *
	 * @param spell
	 */
	public void setSpell(java.lang.String spell) {
		this.spell = spell;
	}
	
	/**
     * 获取城市编码属性
     *
     * @return zipcode
     */
	public java.lang.String getZipcode() {
		return zipcode;
	}
	
	/**
	 * 设置城市编码属性
	 *
	 * @param zipcode
	 */
	public void setZipcode(java.lang.String zipcode) {
		this.zipcode = zipcode;
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
     * 获取属性
     *
     * @return createUser
     */
	public java.lang.String getCreateUser() {
		return createUser;
	}
	
	/**
	 * 设置属性
	 *
	 * @param createUser
	 */
	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemHotspotCity");
        sb.append("{id=").append(id);
        sb.append(", cityName=").append(cityName);
        sb.append(", orderList=").append(orderList);
        sb.append(", spell=").append(spell);
        sb.append(", zipcode=").append(zipcode);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(java.lang.Integer orderList) {
		this.orderList = orderList;
	}

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}
    
}