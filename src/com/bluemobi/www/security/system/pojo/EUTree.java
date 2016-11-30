package com.bluemobi.www.security.system.pojo;

import com.bluemobi.www.data.model.system.SystemMenu;

/**
 * easyui返回的树对象
 * @author lip
 *
 */
public class EUTree implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -341052381487896814L;
	
	public String id;
	
	private String text;
	
	private String iconCls;
	
	private boolean checked = false;
	
	private String state = "close";
	
	private String parent;
	
	private SystemMenu attributes;
	
	private String target;
	
	private String menuUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}	
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public SystemMenu getAttributes() {
		return attributes;
	}

	public void setAttributes(SystemMenu attributes) {
		this.attributes = attributes;
	}		
}
