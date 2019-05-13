package com.koalacute.microkoala.utils.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}

	/**
	 * 获取ip
	 *
	 * @Title: getIp
	 * @Description: getIp
	 * @param servletReques
	 * @return String    返回类型
	 * @throws
	 */
	public static String getIp(HttpServletRequest servletReques){
		String ip = "";
		if(servletReques!=null){
			ip = servletReques.getHeader("x-forwarded-for");
			if(StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)){
				ip = servletReques.getHeader("X-Real-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = servletReques.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = servletReques.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = servletReques.getRemoteAddr();
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = servletReques.getHeader("http_client_ip");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = servletReques.getHeader("HTTP_X_FORWARDED_FOR");
			}
			// 如果是多级代理，那么取第一个ip为客户ip
			if (ip != null && ip.indexOf(",") != -1) {
				ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
			}
			if (ip.indexOf("0:") != -1) {
				ip = "127.0.0.1";
			}
		}
		return ip;
	}
	
	public static String getIp() {
		return getIp(getCurrentRequest());
		
	}
}
