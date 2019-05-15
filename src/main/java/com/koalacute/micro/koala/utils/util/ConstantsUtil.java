package com.koalacute.micro.koala.utils.util;


public class ConstantsUtil {

    //通用的状态码
    public abstract class CommonCode {
        public static final String SUCCESS_CODE = "0";       //获取数据成功状态码
        public static final String FAILED_CODE = "1";       //获取数据失败状态码
        public static final String TIME_OUT = "99";      //会话令牌超时xs
    }

    //通用的消息
    public abstract class CommonMessage {
        public static final String SUCCESS_MESSAGE = "请求数据成功!";    //获取数据失败
        public static final String ERROR_MESSAGE = "请求数据出错!!";   //获取数据出错!
        public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!!"; //参数传递错误
        public static final String COMMON_FAIL_MSG = "网络异常, 请稍后再试";

    }

    public abstract class WeixinResCode {
        public static final String SUCCESS = "0";//成功

    }

    /**
     * 订单锁定状态
     */
    public static class LockedOrder {
        public static final String LOCKED = "locked";
    }

}
