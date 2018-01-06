package com.entity;

public class AchievementCondition {
private String userName;
private String achName;
private String achClassify;
private String startTime;
private String endTime;
private  int achStatus;
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getAchName() {
	return achName;
}
public void setAchName(String achName) {
	this.achName = achName;
}
public String getAchClassify() {
	return achClassify;
}
public void setAchClassify(String achClassify) {
	this.achClassify = achClassify;
}
public String getStartTime() {
	return startTime;
}
public void setStartTime(String startTime) {
	this.startTime = startTime;
}
public String getEndTime() {
	return endTime;
}
public void setEndTime(String endTime) {
	this.endTime = endTime;
}
@Override
public String toString() {
	return "AchievementCondition [userName=" + userName + ", achName="
			+ achName + ", achClassify=" + achClassify + ", startTime="
			+ startTime + ", endTime=" + endTime + "]";
}
public int getAchStatus() {
	return achStatus;
}
public void setAchStatus(int achStatus) {
	this.achStatus = achStatus;
}

}
