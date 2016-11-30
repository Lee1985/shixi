package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.system.SystemHotspotCity;

/**
 * recruit_info实体表()
 * @author sundq
 * @date 2016-02-16 08:55:47
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String title; // 标题
	private java.lang.String type; // 类型 1官方招募 2私人招募
	private java.lang.String cityCode; // 拍摄地
	private java.lang.String publishStatus; // 发布状态 加急
	private java.lang.String lableCode; // 标签
	private java.lang.String director; // 导演
	private java.lang.String screenwriter; // 编剧
	private java.lang.String startDate; // 拍摄开始时间
	private java.lang.String endDate; // 拍摄结束时间
	private java.lang.String deadline; // 试戏截止时间
	private java.lang.String status; // 审核状态 0禁用  1 启用   2 新建审核中  3 修改审核中
	private java.lang.String scriptOutline; // 剧本大纲
	private java.lang.String remark; // 备注
	private java.lang.String memberId; // 发布人
	private java.lang.String createDate;
	private java.lang.String isDelete; // 删除
	private java.lang.Integer viewNum;// 浏览数
	private Date deadlineDate;//截止日期
	private java.lang.String modify;//是否处于修改状态
	
	private java.lang.String startDateStr;//开始时间
	private java.lang.String endDateStr;//结束时间
	private java.lang.String deadlineStr;//截止时间
	
	private MemberInfo memberInfo;// 发布人
	private String ids;
	private String memberName;
	private RecruitCategory category;// 类别
	private SystemHotspotCity city;//拍摄地
	
	private String lableName;// 标签名
	private String nickname;// 姓名
	private String urlPath;// 图片路径
	
	private java.lang.String dateNow;//当前日期
	private java.lang.String lableCodes; // 标签
	private List<String> codeList;
	
	private String hideDeadlineDate;
	private String realNameIdentity;
	
	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

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
     * 获取标题属性
     *
     * @return title
     */
	public java.lang.String getTitle() {		
		return title;
	}
	
	/**
	 * 设置标题属性
	 *
	 * @param title
	 */
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	/**
     * 获取类型 1官方招募 2私人招募属性
     *
     * @return type
     */
	public java.lang.String getType() {
		return type;
	}
	
	/**
	 * 设置类型 1官方招募 2私人招募属性
	 *
	 * @param type
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	/**
     * 获取拍摄地属性
     *
     * @return cityCode
     */
	public java.lang.String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置拍摄地属性
	 *
	 * @param cityCode
	 */
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	
	
	/**
     * 获取标签属性
     *
     * @return lableCode
     */
	public java.lang.String getLableCode() {
		return lableCode;
	}
	
	/**
	 * 设置标签属性
	 *
	 * @param lableCode
	 */
	public void setLableCode(java.lang.String lableCode) {
		this.lableCode = lableCode;
	}
	
	/**
     * 获取导演属性
     *
     * @return director
     */
	public java.lang.String getDirector() {			
		return director;
	}
	
	/**
	 * 设置导演属性
	 *
	 * @param director
	 */
	public void setDirector(java.lang.String director) {
		this.director = director;
	}
	
	/**
     * 获取编剧属性
     *
     * @return screenwriter
     */
	public java.lang.String getScreenwriter() {		
		return screenwriter;
	}
	
	/**
	 * 设置编剧属性
	 *
	 * @param screenwriter
	 */
	public void setScreenwriter(java.lang.String screenwriter) {
		this.screenwriter = screenwriter;
	}
	
	/**
     * 获取审核状态属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置审核状态属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	/**
     * 获取剧本大纲属性
     *
     * @return scriptOutline
     */
	public java.lang.String getScriptOutline() {			
		return scriptOutline;
	}
	
	/**
	 * 设置剧本大纲属性
	 *
	 * @param scriptOutline
	 */
	public void setScriptOutline(java.lang.String scriptOutline) {
		this.scriptOutline = scriptOutline;
	}
	
	/**
     * 获取备注属性
     *
     * @return remark
     */
	public java.lang.String getRemark() {		
		return remark;
	}
	
	/**
	 * 设置备注属性
	 *
	 * @param remark
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	/**
     * 获取发布人属性
     *
     * @return memberId
     */
	public java.lang.String getMemberId() {
		return memberId;
	}
	
	/**
	 * 设置发布人属性
	 *
	 * @param memberId
	 */
	public void setMemberId(java.lang.String memberId) {
		this.memberId = memberId;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RecruitInfo");
        sb.append("{id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", type=").append(type);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", publishStatus=").append(publishStatus);
        sb.append(", lableCode=").append(lableCode);
        sb.append(", director=").append(director);
        sb.append(", screenwriter=").append(screenwriter);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", deadline=").append(deadline);
        sb.append(", status=").append(status);
        sb.append(", scriptOutline=").append(scriptOutline);
        sb.append(", remark=").append(remark);
        sb.append(", memberId=").append(memberId);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getStartDate() {
		return startDate;
	}

	public void setStartDate(java.lang.String startDate) {
		this.startDate = startDate;
	}

	public java.lang.String getEndDate() {
		return endDate;
	}

	public void setEndDate(java.lang.String endDate) {
		this.endDate = endDate;
	}

	public java.lang.String getDeadline() {
		return deadline;
	}

	public void setDeadline(java.lang.String deadline) {
		this.deadline = deadline;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public java.lang.String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(java.lang.String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public java.lang.String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(java.lang.String isDelete) {
		this.isDelete = isDelete;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public RecruitCategory getCategory() {
		return category;
	}

	public void setCategory(RecruitCategory category) {
		this.category = category;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public java.lang.Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(java.lang.Integer viewNum) {
		this.viewNum = viewNum;
	}

	public java.lang.String getDateNow() {
		return dateNow;
	}

	public void setDateNow(java.lang.String dateNow) {
		this.dateNow = dateNow;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public SystemHotspotCity getCity() {
		return city;
	}

	public void setCity(SystemHotspotCity city) {
		this.city = city;
	}

	public java.lang.String getModify() {
		return modify;
	}

	public void setModify(java.lang.String modify) {
		this.modify = modify;
	}

	public java.lang.String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(java.lang.String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public java.lang.String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(java.lang.String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public java.lang.String getDeadlineStr() {
		return deadlineStr;
	}

	public void setDeadlineStr(java.lang.String deadlineStr) {
		this.deadlineStr = deadlineStr;
	}
	public java.lang.String getLableCodes() {
		return lableCodes;
	}

	public void setLableCodes(java.lang.String lableCodes) {
		this.lableCodes = lableCodes;
	}

	public String getHideDeadlineDate() {
		return hideDeadlineDate;
	}

	public void setHideDeadlineDate(String hideDeadlineDate) {
		this.hideDeadlineDate = hideDeadlineDate;
	}

	public String getRealNameIdentity() {
		return realNameIdentity;
	}

	public void setRealNameIdentity(String realNameIdentity) {
		this.realNameIdentity = realNameIdentity;
	}		
}