package com.dao;

import com.entity.Audit;

public interface AuditMapper {
	public int insertAudit(Audit audit);//插入一个记录
	public int deleteAudit(Audit audit);//删除一个记录
	public Audit selectAudit(int achId);//选择一个记录
	public int updateAudit(int audId);//更新一条记录
}
