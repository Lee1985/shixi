package com.bluemobi.www.data.model.recruit;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;
import com.bluemobi.www.data.model.member.MemberInfo;

/**
 * recruit_informant_info实体表()
 * @author sundq
 * @date 2016-02-18 11:02:27
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class RecruitInformantInfo extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String informantsId; // 举报人
	private java.lang.String beInformantsId; // 被举报人
	private java.lang.String recruitId; // 被举报招募ID
	private java.lang.String typeId; // 举报类型
	private java.lang.String content; // 举报内容
	private java.lang.String createDate; // 
	private java.lang.String status; // 状态
	
	private MemberInfo informantsInfo;//举报人信息
	private MemberInfo beInformantsInfo;//被举报人信息
	private RecruitInfo recruitInfo;//招募信息
	private RecruitInformantType informantType;//举报类别
	
	private String ids;
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
     * 获取举报人属性
     *
     * @return informantsId
     */
	public java.lang.String getInformantsId() {
		return informantsId;
	}
	
	/**
	 * 设置举报人属性
	 *
	 * @param informantsId
	 */
	public void setInformantsId(java.lang.String informantsId) {
		this.informantsId = informantsId;
	}
	
	/**
     * 获取被举报人属性
     *
     * @return beInformantsId
     */
	public java.lang.String getBeInformantsId() {
		return beInformantsId;
	}
	
	/**
	 * 设置被举报人属性
	 *
	 * @param beInformantsId
	 */
	public void setBeInformantsId(java.lang.String beInformantsId) {
		this.beInformantsId = beInformantsId;
	}
	
	/**
     * 获取被举报招募ID属性
     *
     * @return recruitId
     */
	public java.lang.String getRecruitId() {
		return recruitId;
	}
	
	/**
	 * 设置被举报招募ID属性
	 *
	 * @param recruitId
	 */
	public void setRecruitId(java.lang.String recruitId) {
		this.recruitId = recruitId;
	}
	
	/**
     * 获取举报类型属性
     *
     * @return typeId
     */
	public java.lang.String getTypeId() {
		return typeId;
	}
	
	/**
	 * 设置举报类型属性
	 *
	 * @param typeId
	 */
	public void setTypeId(java.lang.String typeId) {
		this.typeId = typeId;
	}
	
	/**
     * 获取举报内容属性
     *
     * @return content
     */
	public java.lang.String getContent() {
		return content;
	}
	
	/**
	 * 设置举报内容属性
	 *
	 * @param content
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	/**
     * 获取状态属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置状态属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RecruitInformantInfo");
        sb.append("{id=").append(id);
        sb.append(", informantsId=").append(informantsId);
        sb.append(", beInformantsId=").append(beInformantsId);
        sb.append(", recruitId=").append(recruitId);
        sb.append(", typeId=").append(typeId);
        sb.append(", content=").append(content);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
		sb.append('}');
        return sb.toString();
    }

	public java.lang.String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}

	public MemberInfo getInformantsInfo() {
		return informantsInfo;
	}

	public void setInformantsInfo(MemberInfo informantsInfo) {
		this.informantsInfo = informantsInfo;
	}

	public MemberInfo getBeInformantsInfo() {
		return beInformantsInfo;
	}

	public void setBeInformantsInfo(MemberInfo beInformantsInfo) {
		this.beInformantsInfo = beInformantsInfo;
	}

	public RecruitInfo getRecruitInfo() {
		return recruitInfo;
	}

	public void setRecruitInfo(RecruitInfo recruitInfo) {
		this.recruitInfo = recruitInfo;
	}

	public RecruitInformantType getInformantType() {
		return informantType;
	}

	public void setInformantType(RecruitInformantType informantType) {
		this.informantType = informantType;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
    
}