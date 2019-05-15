package com.koalacute.micro.koala.utils.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {

    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64);
    }

    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }
}
