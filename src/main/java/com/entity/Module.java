package com.entity;

public class Module {
private int modId;
private String modName;
private String modContent;
private String modDescribe;
private int achId;
public Module(int modId, String modName, String modContent, String modDescribe,int achId) {
	super();
	this.modId = modId;
	this.modName = modName;
	this.modContent = modContent;
	this.modDescribe = modDescribe;
	this.achId= achId;
}
public Module() {
	
}
public int getModId() {
	return modId;
}
public void setModId(int modId) {
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
public int getAchId() {
	return achId;
}
public void setAchId(int achId) {
	this.achId = achId;
}


}
