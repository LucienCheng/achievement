package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Audit;

public interface AuditMapper {
	public int insertAudits(@Param("achIds") List<Integer> achIds,@Param("audDate")String audDate,
			@Param("auditorId")Integer auditorId);//插入一个记录
	public int insertAudit(Audit audit);//插入一个记录
	public int deleteAudit(Audit audit);//删除一个记录
	public Audit selectAudit(int achId);//选择一个记录
}
