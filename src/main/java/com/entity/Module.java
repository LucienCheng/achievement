package com.entity;

public class Module {
private Integer modId;
private String modName;
private String modContent;
private String modDescribe;
private Integer achId;
public Module(Integer modId, String modName, String modContent, String modDescribe,Integer achId) {
	super();
	this.modId = modId;
	this.modName = modName;
	this.modContent = modContent;
	this.modDescribe = modDescribe;
	this.achId= achId;
}
public Module() {
	
}
public Integer getModId() {
	return modId;
}
public void setModId(Integer modId) {
	this.modId = modId;
}
public String getModName() {
	return modName;
}
public void setModName(String modName) {
	this.modName = modName;
}
public String getModContent() {
	return modContent;
}
public void setModContent(String modContent) {
	this.modContent = modContent;
}
public String getModDescribe() {
	return modDescribe;
}
public void setModDescribe(String modDescribe) {
	this.modDescribe = modDescribe;
}
@Override
public String toString() {
	return "Module [modId=" + modId + ", modName=" + modName + ", modContent="
			+ modContent + ", modDescribe=" + modDescribe + "]";
}
public Integer getAchId() {
	return achId;
}
public void setAchId(Integer achId) {
	this.achId = achId;
}


}
