package com.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestJson {
	@NotNull(message="不能为空")
	@Size(min=1,max=10,message="不能没有字符")
	private String userName;
	private String address;
	@Valid
	private Role role;
	public TestJson() {
		// TODO Auto-generated constructor stub
		role=new Role();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "TestJson [userName=" + userName + ", address=" + address
				+ ", role=" + role + "]";
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

}
