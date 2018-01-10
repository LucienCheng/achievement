package com.entity;

public class AchievementCondition {
	private Integer authorId;
	private Integer auditorId;
	private Integer achStatus;
	private String achStartTime;
	private String achEndTime;
	private String audStartTime;
	private String audEndTime;
	private String achName;
	private String authorName;
	private String auditorName;
	private String achClassify;
	public String getAchClassify() {
		return achClassify;
	}
	public void setAchClassify(String achClassify) {
		this.achClassify = achClassify;
	}
	public String getAchLock() {
		return achLock;
	}
	public void setAchLock(String achLock) {
		this.achLock = achLock;
	}
	private String achLock;
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Integer getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}
	public Integer getAchStatus() {
		return achStatus;
	}
	public void setAchStatus(Integer achStatus) {
		this.achStatus = achStatus;
	}
	public String getAchStartTime() {
		return achStartTime;
	}
	public void setAchStartTime(String achStartTime) {
		this.achStartTime = achStartTime;
	}
	public String getAchEndTime() {
		return achEndTime;
	}
	public void setAchEndTime(String achEndTime) {
		this.achEndTime = achEndTime;
	}
	public String getAudStartTime() {
		return audStartTime;
	}
	public void setAudStartTime(String audStartTime) {
		this.audStartTime = audStartTime;
	}
	public String getAudEndTime() {
		return audEndTime;
	}
	public void setAudEndTime(String audEndTime) {
		this.audEndTime = audEndTime;
	}
	public String getAchName() {
		return achName;
	}
	public void setAchName(String achName) {
		this.achName = achName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	@Override
	public String toString() {
		return "AchievementCondition [authorId=" + authorId + ", auditorId="
				+ auditorId + ", achStatus=" + achStatus + ", achStartTime="
				+ achStartTime + ", achEndTime=" + achEndTime
				+ ", audStartTime=" + audStartTime + ", audEndTime="
				+ audEndTime + ", achName=" + achName + ", authorName="
				+ authorName + ", auditorName=" + auditorName
				+ ", achClassify=" + achClassify + ", achLock=" + achLock + "]";
	}

	

}
