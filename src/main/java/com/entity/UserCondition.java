package com.entity;

public class UserCondition {
	private Integer userId;
	private String userName;
	private String userWorkNum;
	public String getUserWorkNum() {
		return userWorkNum;
	}
	public void setUserWorkNum(String userWorkNum) {
		this.userWorkNum = userWorkNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "UserCondition [userId=" + userId + ", userName=" + userName
				+ ", userWorkNum=" + userWorkNum + "]";
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
