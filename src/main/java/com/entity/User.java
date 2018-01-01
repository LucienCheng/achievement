package com.entity;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="user")
@Scope(value="prototype")
public class User implements Serializable {

	private static final long serialVersionUID = -6985849510857392350L;// 序列化，防止数据丢失
	private int userId;
	private String userWorkNum;
	private String userPassword;
	private String userPhone;
	private String userName;
	private int userSex;
	@Resource(name="role")
	private Role role;
	private String userPos;

	public User() {

	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userWorkNum=" + userWorkNum
				+ ", userPassword=" + userPassword + ", userPhone=" + userPhone
				+ ", userName=" + userName + ", userSex=" + userSex + ", role="
				+ role + ", userPos=" + userPos + "]";
	}

	public User(int userId, String userWorkNum, String userPassword,
			String userPhone, String userName, int userSex, Role role,
			String userPos) {
		super();
		this.userId = userId;
		this.userWorkNum = userWorkNum;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userName = userName;
		this.userSex = userSex;
		this.role = role;
		this.userPos = userPos;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserWorkNum() {
		return userWorkNum;
	}

	public void setUserWorkNum(String userWorkNum) {
		this.userWorkNum = userWorkNum;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public int getUserSex() {
		return userSex;
	}

	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserPos() {
		return userPos;
	}

	public void setUserPos(String userPos) {
		this.userPos = userPos;
	}
}
