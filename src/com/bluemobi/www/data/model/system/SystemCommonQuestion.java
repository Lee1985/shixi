package com.bluemobi.www.data.model.system;

import java.io.Serializable;
import com.bluemobi.www.data.model.BaseEntity;

/**
 * system_common_question实体表()
 * @author sundq
 * @date 2016-02-02 14:48:12
 * @project 上海科匠信息科技有限公司 2015
 */
 @SuppressWarnings("serial")
public class SystemCommonQuestion extends BaseEntity implements Serializable {
	private java.lang.String id; // 
	private java.lang.String question; // 问题
	private java.lang.String answer; // 答案
	private java.lang.String createDate; // 创建时间
	private java.lang.String createUser; // 
	private java.lang.String status; // 
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
     * 获取问题属性
     *
     * @return question
     */
	public java.lang.String getQuestion() {
		return question;
	}
	
	/**
	 * 设置问题属性
	 *
	 * @param question
	 */
	public void setQuestion(java.lang.String question) {
		this.question = question;
	}
	
	/**
     * 获取答案属性
     *
     * @return answer
     */
	public java.lang.String getAnswer() {
		return answer;
	}
	
	/**
	 * 设置答案属性
	 *
	 * @param answer
	 */
	public void setAnswer(java.lang.String answer) {
		this.answer = answer;
	}
	
	
	/**
     * 获取属性
     *
     * @return createUser
     */
	public java.lang.String getCreateUser() {
		return createUser;
	}
	
	/**
	 * 设置属性
	 *
	 * @param createUser
	 */
	public void setCreateUser(java.lang.String createUser) {
		this.createUser = createUser;
	}
	
	/**
     * 获取属性
     *
     * @return status
     */
	public java.lang.String getStatus() {
		return status;
	}
	
	/**
	 * 设置属性
	 *
	 * @param status
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SystemCommonQuestion");
        sb.append("{id=").append(id);
        sb.append(", question=").append(question);
        sb.append(", answer=").append(answer);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
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
    
}