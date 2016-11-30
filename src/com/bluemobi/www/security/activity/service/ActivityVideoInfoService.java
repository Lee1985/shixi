package com.bluemobi.www.security.activity.service;

import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.security.base.service.BaseService;

public interface ActivityVideoInfoService extends BaseService<ActivityVideoInfo>{

	public int addShareNum(ActivityVideoInfo info);
}
