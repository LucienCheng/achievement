package com.service;

import java.util.List;

import com.entity.Module;

public interface ModuleService {
	public int insertModule(Module module);
	public int updateModule(Module module);
	public int deleteModule(List<Integer> modules);
	public Module selectModuleByModId(int modId);
	public List<Module> selectModuleByAchId(int achId);
}
