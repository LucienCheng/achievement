package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Achievement;
import com.entity.AchievementCondition;

public interface AchiMapper {
	public int updateAchiWithLock(@Param("achIds")List<Integer> achIds, @Param("achLock")Integer achLock) ;
	public int updateAchiWithSta(@Param("achIds")List<Integer> achIds, @Param("achStatus")Integer achStatus) ;
	public int insertAchi(Achievement achievement);//插入一条数据
	public int updateAchi(Achievement achievement);//更新一个成果
	public int deleteAchis(List<Integer> achievements);//批量删除成果
	public int selectCount(AchievementCondition condition);//获得总数
	public int updateAchiWithModify(@Param("achIds")List<Integer> achIds,@Param("isModify")Integer isModify) ;
/**
 * 
 * @param achId 成果id
 * @param authorId 作者id
 * @param auditorId 审核人员id
 * @param achName 成果名字
 * @param achClassify 成果分类
 * @param achStatus 成果状态
 * @param authorName 作者名字
 * @param achStartTime 成果创建开始时间
 * @param achEndTime 成果创建结束时间
 * @param audStartTime 成果审核开始时间
 * @param audEndTime 成果审核结束时间
 * @param start 页码
 * @param count 个数
 * @param condition 是最新，还是最热
 * @return
 */
	public List<Achievement> searchAchi(@Param("achId") Integer achId, @Param("authorId")Integer authorId,@Param("auditorId") Integer auditorId,
			@Param("achName")String achName,@Param("achClassify")String achClassify,
			@Param("achStatus")Integer achStatus,@Param("authorName")String authorName,@Param("achStartTime") String achStartTime,
			@Param("achEndTime") String achEndTime,
			@Param("audStartTime") String audStartTime,@Param("auditorName")String auditorName,
			@Param("audEndTime") String audEndTime,@Param("achLock") Integer achLock,
			@Param("start")Integer start,@Param("count")Integer count,@Param("condition") int condition);//总的搜索
	
}
