package com.entity;

public class Achievement {
	private Integer achId;
	private String achName;
	private User user;
	private Integer achStatus;
	private String achVideoPath;
	private int achLock;
	private String achImagePath;
	private int isModify;
	private String achClassify;
	private int achCTR;
	private String achDescribe;
	private String achDate;
	public Achievement(int achId, String achName, User user, int achStatus,
			String achVideoPath, int achLock, String achImagePath,
			int isModify, String achClassify, int achCTR, String achDescribe,
			String achDate) {
		super();
		this.achId = achId;
		this.achName = achName;
		this.user = user;
		this.setAchStatus(achStatus);
		this.achVideoPath = achVideoPath;
		this.achLock = achLock;
		this.achImagePath = achImagePath;
		this.isModify = isModify;
		this.achClassify = achClassify;
		this.achCTR = achCTR;
		this.achDescribe = achDescribe;
		this.achDate = achDate;
	}
	public Achievement() {
	}

public Integer getAchId() {
		return achId;
	}
	public void setAchId(Integer achId) {
		this.achId = achId;
	}
	public String getAchName() {
		return achName;
	}
	public void setAchName(String achName) {
		this.achName = achName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getAchVideoPath() {
		return achVideoPath;
	}
	public void setAchVideoPath(String achVideoPath) {
		this.achVideoPath = achVideoPath;
	}
	public int getAchLock() {
		return achLock;
	}
	public void setAchLock(int achLock) {
		this.achLock = achLock;
	}
	public String getAchImagePath() {
		return achImagePath;
	}
	public void setAchImagePath(String achImagePath) {
		this.achImagePath = achImagePath;
	}
	public int getIsModify() {
		return isModify;
	}
	public void setIsModify(int isModify) {
		this.isModify = isModify;
	}
	public String getAchClassify() {
		return achClassify;
	}
	public void setAchClassify(String achClassify) {
		this.achClassify = achClassify;
	}
	public int getAchCTR() {
		return achCTR;
	}
	public void setAchCTR(int achCTR) {
		this.achCTR = achCTR;
	}
	public String getAchDescribe() {
		return achDescribe;
	}
	public void setAchDescribe(String achDescribe) {
		this.achDescribe = achDescribe;
	}
	public String getAchDate() {
		return achDate;
	}
	public void setAchDate(String achDate) {
		this.achDate = achDate;
	}
	public Integer getAchStatus() {
		return achStatus;
	}
	public void setAchStatus(Integer achStatus) {
		this.achStatus = achStatus;
	}

}
