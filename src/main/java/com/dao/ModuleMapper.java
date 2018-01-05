package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Module;

public interface ModuleMapper {
	public int insertModule(Module module);
	//实际上并不是更新里面的内容，而是copy一份
	public int updateModule(Module module);
	public int deleteModule(List<Integer> modules);
	public Module selectModuleByModId(int modId);
	public List<Module> selectModuleByAchId(int achId);
	public int updateModuleByachId(@Param("moduleIds")List<Integer> moduleIds,int achievementId);
}
