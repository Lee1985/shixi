package com.bluemobi.www.constant;

public enum DiscoverTypeEnum {
		
	ADVERTISEMENT(1,"广告"),
	RECRUITMENT_RECOMMENDATION(2,"招募推荐"),
	IMPORTANT_RECRUITMENT(3,"急聘信息"),
	ACTING_ACTIVITY(4,"彪戏活动"),
	COMPANY_INNER_ACTIVITY(5,"公司内部活动");
	
	private int code;
	private String name;
	
	private DiscoverTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getName(int code) {
		for (DiscoverTypeEnum e : DiscoverTypeEnum.values()) {
			if (e.getCode() == code) {
				return e.name;
			}
		}
		return "";
	}
	
	public static int getCode(String name) {
		for (DiscoverTypeEnum e : DiscoverTypeEnum.values()) {
			if (e.getName().equals(name)) {
				return e.code;
			}
		}
		return -1;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
