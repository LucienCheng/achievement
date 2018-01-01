package com.entity;

public class Audit {
private int audId;
private int userId;
private int achId;
private String audDate;
private int audShow;
private String opinion;

public Audit(int audId, int userId, int achId, String audDate,
		int audShow, String opinion) {
	super();
	this.audId = audId;
	this.userId = userId;
	this.audDate = audDate;
	this.audShow = audShow;
	this.opinion = opinion;
	this.achId=achId;
}

public Audit() {
}

public int getAudId() {
	return audId;
}

public void setAudId(int audId) {
	this.audId = audId;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public int getAchId() {
	return achId;
}

public void setAchId(int achId) {
	this.achId = achId;
}

public String getAudDate() {
	return audDate;
}

public void setAudDate(String audDate) {
	this.audDate = audDate;
}

public int getAudShow() {
	return audShow;
}

public void setAudShow(int audShow) {
	this.audShow = audShow;
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
			+ ", audDate=" + audDate + ", audShow=" + audShow + ", opinion="
			+ opinion + "]";
}


}
