package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Module;

public interface ModuleMapper {
	public int updateModules(List<Module> modules);
	public int deleteModule(List<Integer> modules);
	public Module selectModuleByModId(int modId);
	public List<Module> selectModuleByAchId(int achId);
	public int insertModules(List<Module> modules);
}
