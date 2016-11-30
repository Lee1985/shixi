package com.bluemobi.www.constant;

public enum ReplyEnum {
	
	RESUME(1,"简历"),
	AUDITION_VIDEO(2,"试戏视频"),
	RECRUIT(3,"招募"),
	ACTIVITY_VIDEO(4,"活动视频"),
	REALNAME_IDENTITY(5,"实名认证"),
	EDUCATION_IDENTITY(6,"学历认证");
	
	private int code;
	private String name;
	
	private ReplyEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getName(int code) {
		for (ReplyEnum e : ReplyEnum.values()) {
			if (e.getCode() == code) {
				return e.name;
			}
		}
		return "";
	}
	
	public static int getCode(String name) {
		for (ReplyEnum e : ReplyEnum.values()) {
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
