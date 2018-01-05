package com.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.ModuleMapper;
import com.entity.Module;
import com.service.ModuleService;

@Service("moduleImpl")
public class ModuleImpl implements ModuleService{
	@Resource
	private ModuleMapper moduleMapper;

	@Override
	public int insertModule(Module module) {
		// TODO Auto-generated method stub
		return moduleMapper.insertModule(module);
	}

	@Override
	public int updateModule(Module module) {
		// TODO Auto-generated method stub
		return moduleMapper.updateModule(module);
	}

	@Override
	public int deleteModule(List<Integer> modules) {
		// TODO Auto-generated method stub
		return moduleMapper.deleteModule(modules);
	}

	@Override
	public Module selectModuleByModId(int modId){
		// TODO Auto-generated method stub
		return moduleMapper.selectModuleByModId(modId);
	}

	@Override
	public List<Module> selectModuleByAchId(int achId) {
		// TODO Auto-generated method stub
		return moduleMapper.selectModuleByAchId(achId);
	}

	@Override
	public int updateModuleByachId(List<Integer> moduleIds,int achievementId) {
		// TODO Auto-generated method stub
		return moduleMapper.updateModuleByachId(moduleIds,achievementId);
	}

}
