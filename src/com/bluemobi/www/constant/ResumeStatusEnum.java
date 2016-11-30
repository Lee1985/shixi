package com.bluemobi.www.constant;

public enum ResumeStatusEnum {
	
	UNCOMMITED(0,"未提交"),//简历完善度不够
	NEW_AUDTING(1,"新建审核中"),
	AUDITED(2,"审核通过"),
	AUDIT_REFUSED(3,"审核拒绝"),
	MODIFY_AUDITING(4,"修改审核中");
	
	private int code;
	private String name;
	
	private ResumeStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static String getName(int code) {
		for (ResumeStatusEnum e : ResumeStatusEnum.values()) {
			if (e.getCode() == code) {
				return e.name;
			}
		}
		return "";
	}
	
	public static int getCode(String name) {
		for (ResumeStatusEnum e : ResumeStatusEnum.values()) {
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
