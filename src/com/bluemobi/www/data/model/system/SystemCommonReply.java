package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_common_reply实体表()
 * @author sundq
 * @date 2016-02-26 09:15:03
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemCommonReply extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.Integer orderList; // 排序
	private java.lang.String content; // 内容
	private java.lang.String createDate; // 创建时间
	private Integer type;//类型
	private String typeName;
	
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
     * 获取内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemCommonReply");
        sb.append("{id=").append(id);
        sb.append(", orderList=").append(orderList);
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	} 		
}