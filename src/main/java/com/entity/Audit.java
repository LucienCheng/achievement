package com.entity;

public class Audit {
private Integer audId;
private Integer userId;
private Integer achId;
private String audDate;
private String opinion;



public Audit() {
}

public Integer getAudId() {
	return audId;
}

public void setAudId(Integer audId) {
	this.audId = audId;
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public Integer getAchId() {
	return achId;
}

public void setAchId(Integer achId) {
	this.achId = achId;
}

public String getAudDate() {
	return audDate;
}

public void setAudDate(String audDate) {
	this.audDate = audDate;
}



public String getOpinion() {
	return opinion;
}

public void setOpinion(String opinion) {
	this.opinion = opinion;
}

@Override
public String toString() {
	return "Audit [audId=" + audId + ", userId=" + userId + ", achId=" + achId
			+ ", audDate=" + audDate + ", opinion="
			+ opinion + "]";
}


}
