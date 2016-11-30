package com.bluemobi.www.utils;

import com.bluemobi.www.identifier.Generator;
import com.bluemobi.www.identifier.UUIDGenerator;

public class IdentifierUtils {

	/**
	 * 生成唯一识别码
	 * 
	 * @return
	 */
	public static Generator getId() {
		return new UUIDGenerator();
	}
}
