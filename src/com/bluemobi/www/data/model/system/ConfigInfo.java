package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * config_info实体表()
 * @author lip
 * @date 2016-06-06 17:29:33
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class ConfigInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 主键ID
	private java.lang.String key; // 系统设置Key
	private java.lang.String value; // 系统设置Value
	private java.lang.String name; // 系统设置Value
	/**
     * 获取主键ID属性
     *
     * @return id
     */
	public java.lang.String getId() {
		return id;
	}
	
	/**
	 * 设置主键ID属性
	 *
	 * @param id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
     * 获取系统设置Key属性
     *
     * @return key
     */
	public java.lang.String getKey() {
		return key;
	}
	
	/**
	 * 设置系统设置Key属性
	 *
	 * @param key
	 */
	public void setKey(java.lang.String key) {
		this.key = key;
	}
	
	/**
     * 获取系统设置Value属性
     *
     * @return value
     */
	public java.lang.String getValue() {
		return value;
	}
	
	/**
	 * 设置系统设置Value属性
	 *
	 * @param value
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	
	/**
     * 获取系统设置Value属性
     *
     * @return name
     */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置系统设置Value属性
	 *
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ConfigInfo");
        sb.append("{id=").append(id);
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append(", name=").append(name);
		sb.append('}');
        return sb.toString();
    }
    
}