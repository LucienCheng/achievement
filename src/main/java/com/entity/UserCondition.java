package com.entity;

public class UserCondition {
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
		return "UserCondition [userName=" + userName + ", userWorkNum="
				+ userWorkNum + "]";
	}
	
}
