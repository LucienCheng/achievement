package com.service;

import java.util.List;

import com.entity.Module;

public interface ModuleService {
	public int updateModules(List<Module> module);
	public int deleteModule(List<Integer> modules);
	public Module selectModuleByModId(int modId);
	public List<Module> selectModuleByAchId(int achId);
	public int insertModules(List<Module> modules,int achId);
}
