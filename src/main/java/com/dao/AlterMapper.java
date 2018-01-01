package com.dao;

import org.apache.ibatis.annotations.Param;

public interface AlterMapper {
	public int insertAchiAlter(@Param("oldId")int oldId,@Param("newId")int newId);//插入一个修改记录
	public int deleteAchiAlter(@Param("newId")int newId);//删除一条修改记录
	public int selectAlter(@Param("newId") int newId);//搜索修改记录
}
