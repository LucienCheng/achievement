package com.service;

import com.entity.Audit;

public interface AuditService {
	/*
	 * 用于审核人员提出意见的更新。
	 */
	public boolean updateAudit(int userId,int achId,Audit audit);
}
