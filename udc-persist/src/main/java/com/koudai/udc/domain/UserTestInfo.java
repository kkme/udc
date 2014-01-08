package com.koudai.udc.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class UserTestInfo implements Serializable {

	private static final long serialVersionUID = -6483363560014164446L;
	private Long id;
	private String userId;
	private String questionId;
	private String answerId;

	public UserTestInfo() {

	}

	public UserTestInfo(String userId, String questionId, String answerId) {
		this.userId = userId;
		this.questionId = questionId;
		this.answerId = answerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getFormatDressStyle() {
		StringBuffer sb = new StringBuffer();
		sb.append(questionId);
		if (answerId.contains(",")) {
			List<String> answerIds = Arrays.asList(answerId.split(","));
			for (String answer : answerIds) {
				sb.append("_").append(answer);
			}
		} else {
			sb.append("_").append(answerId);
		}
		return sb.toString();
	}

}
