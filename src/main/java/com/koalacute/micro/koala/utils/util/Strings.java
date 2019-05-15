package com.koalacute.micro.koala.utils.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * User: weimeng
 * Date: 2018/5/21 13:39
 */
public abstract class Strings {

    /**
     * 先对src进行trim，然后截取到end位(不包含)
     */
    public static String trimThenSubString(String src, int end) {
        return StringUtils.substring(StringUtils.trim(src), 0, end);
    }
}
