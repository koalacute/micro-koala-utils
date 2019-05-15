package com.koalacute.micro.koala.utils.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils{
	/**
	 * 通过传入的字符串，和分隔符拼接字符串
	 * @return
	 */
	public static String getAppendString(String reg,String... str){
		StringBuffer sb = new StringBuffer();
		String result = "";
		if(null != str){
			for(String s:str){
				sb.append(s + reg);
			}
			result = sb.toString().substring(0, sb.length()-1);
		}
		return result;
	}
	
	/**
	 * 去掉中文字
	 */
	public static String deleteChinese(String billPeriod){
		
		//正则表达式 去中文
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(billPeriod);
        billPeriod = mat.replaceAll("");
        return billPeriod;
	}


	/**
	 * 通过有效期间获取身份过期时间
	 */
	public static String getIdentityExpiredTimeByValidDate(String validDate) {
		if (StringUtils.isBlank(validDate)) {
			return "";
		}
		String[] splitString = validDate.split("-");
		if (2 != splitString.length) {
			return "";
		}
		return splitString[1];
	}
}
