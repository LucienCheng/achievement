package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @ClassName: ConvertTimeServicer
* @author 
* @date 2017年11月29日 下午3:08:50
* @Description: 这是一个转化工具类，将数据库里的时间戳变成字符串，或者从字符串转化变成时间戳存入数据库
*
 */
public class TimeToolService {
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	* @Title: convertToString
	* @param @param time
	* @param @return 
	* @return String   
	* @throws
	* @Description:将一个时间戳变成字符串
	 */
	public static String convertToString(Date date){
		return format.format(date);
	}
	/**
	 * 
	* @Title: convertToLong
	* @param @param time
	* @param @return
	* @param @throws ParseException 
	* @return long   
	* @throws
	* @Description:将一个时间字符变成一个时间
	 */
	public static Date convertToLong(String time) throws ParseException {
		Date date=format.parse(time);
		return date;
	}
	/**
	 * 
	* @Title: getCurrentTime
	* @param @return 
	* @return long   
	* @throws
	* @Description:返回一个当前时间字符
	 */
	public static String getCurrentTime(){
		return format.format(new Date(System.currentTimeMillis()));
	}
}
