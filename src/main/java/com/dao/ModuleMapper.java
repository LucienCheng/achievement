package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Module;

public interface ModuleMapper {
	public int updateModules(@Param("module")Module module);
	public int deleteModule(List<Integer> modules);
	public Module selectModuleByModId(int modId);
	public List<Module> selectModuleByAchId(int achId);
	public int insertModules(@Param("module")Module module);
}
