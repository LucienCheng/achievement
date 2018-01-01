package com.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.AuditMapper;
import com.entity.Audit;
import com.service.AuditService;
@Service("AuditImpl")
public class AuditImpl implements AuditService {
	@Resource
	private AuditMapper auditMapper;
	static String getTime(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	/*更新审核结果*/
	@Override
	public boolean updateAudit(int userId,int achId, Audit auditNew) {
		// TODO Auto-generated method stub
		auditNew.setAudDate(getTime());
		auditNew.setAchId(achId);
		auditNew.setUserId(userId);
		if(auditMapper.selectIsAudit(achId)==0){
			auditMapper.insertAudit(auditNew);
		}
		else {
			Audit auditOld=auditMapper.selectAudit(achId);
			auditMapper.updateAudit(auditOld.getAudId());
			auditMapper.insertAudit(auditNew);
		}
		return true;
	}
}
