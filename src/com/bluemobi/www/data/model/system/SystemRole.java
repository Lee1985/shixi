package com.bluemobi.www.data.model.system;

import java.io.Serializable;

import com.bluemobi.www.data.model.BaseEntity;

/**
 * 
 * @ClassName: SystemRole
 * @Description: 系统角色信息
 * @author guoc
 * @date 2015-3-20 下午4:54:53
 * @Copyright：上海科匠信息科技有限公司
 */
@SuppressWarnings("serial")
public class SystemRole extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private String id;
	// 角色名称
	private String name;
	// 备注
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
