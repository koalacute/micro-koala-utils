package com.koalacute.micro.koala.utils.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;

import java.util.Map;

/**
 * 对已有的HttpClientUtils进行简单包装
 * User: weimeng
 * Date: 2018/7/10 11:11
 */
public class HttpClient2Utils {

    private static final int socketTimeout = 20000; //20秒
    private static final int connectionTimeout = 7000; //7秒

    /**
     * Get方法
     */
    public static String doGet(String address) throws Exception {
        return HttpClientUtils.getMethodGetResponse(address, getDefaultTimeOutConfig());
    }

    /**
     * Get方法 + Header
     */
    public static String doGetWithHeader(String address, Map<String, String> headerMap) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return HttpClientUtils.getMethodGetResponseWithHeader(address, config, headerMap);
    }

    /**
     * Post方法
     */
    public static String doPost(String address, HttpEntity paramEntity) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return HttpClientUtils.getMethodPostResponse(address, paramEntity, config);
    }

    /**
     * Post方法 + Header
     */
    public static String doPostWithHeader(String address, HttpEntity paramEntity, Map<String, String> headerMap) throws Exception {
        RequestConfig config = getDefaultTimeOutConfig();
        return HttpClientUtils.getMethodPostResponseWithHeader(address, paramEntity, config, headerMap);
    }


    private static RequestConfig getDefaultTimeOutConfig() {
        return getTimeOutConfig(socketTimeout, connectionTimeout);
    }

    private static RequestConfig getTimeOutConfig(int socketTimeout, int connectionTimeout) {
        return RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectionTimeout).build();
    }
}
