package com.bluemobi.www.data.model.system;

import java.io.Serializable;

import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_file_info实体表()
 * @author 郭冲
 * @date 2015-06-23 15:39:06
 * @project 蓝色互动 2015
 */
 @SuppressWarnings("serial")
public class SystemFileInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String uuid; // 
	private java.lang.String urlPath; // 
	private java.lang.Long fsize; // 
	private java.lang.String ftype; // 
	private java.lang.String suffix; // 
	private java.lang.String name; // 
	private java.lang.String cdate; // 
	private java.lang.String params; // 
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
     * @return uuid
     */
	public java.lang.String getUuid() {
		return uuid;
	}
	
	/**
	 * 设置属性
	 *
	 * @param uuid
	 */
	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
	}
	/**
     * 获取属性
     *
     * @return urlPath
     */
	public java.lang.String getUrlPath() {
		return urlPath;
	}
	
	/**
	 * 设置属性
	 *
	 * @param urlPath
	 */
	public void setUrlPath(java.lang.String urlPath) {
		this.urlPath = urlPath;
	}
	/**
     * 获取属性
     *
     * @return fsize
     */
	public java.lang.Long getFsize() {
		return fsize;
	}
	
	/**
	 * 设置属性
	 *
	 * @param fsize
	 */
	public void setFsize(java.lang.Long fsize) {
		this.fsize = fsize;
	}
	/**
     * 获取属性
     *
     * @return ftype
     */
	public java.lang.String getFtype() {
		return ftype;
	}
	
	/**
	 * 设置属性
	 *
	 * @param ftype
	 */
	public void setFtype(java.lang.String ftype) {
		this.ftype = ftype;
	}
	/**
     * 获取属性
     *
     * @return suffix
     */
	public java.lang.String getSuffix() {
		return suffix;
	}
	
	/**
	 * 设置属性
	 *
	 * @param suffix
	 */
	public void setSuffix(java.lang.String suffix) {
		this.suffix = suffix;
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
     * @return cdate
     */
	public java.lang.String getCdate() {
		return cdate;
	}
	
	/**
	 * 设置属性
	 *
	 * @param cdate
	 */
	public void setCdate(java.lang.String cdate) {
		this.cdate = cdate;
	}
	/**
     * 获取属性
     *
     * @return params
     */
	public java.lang.String getParams() {
		return params;
	}
	
	/**
	 * 设置属性
	 *
	 * @param params
	 */
	public void setParams(java.lang.String params) {
		this.params = params;
	}
}