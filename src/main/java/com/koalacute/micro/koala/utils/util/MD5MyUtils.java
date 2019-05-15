package com.koalacute.micro.koala.utils.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5MyUtils {

    private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'                     };

    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        int t;
        for (int i = 0; i < 16; i++) {
            t = bytes[i];
            if (t < 0)
                t += 256;
            sb.append(hexDigits[(t >>> 4)]);
            sb.append(hexDigits[(t % 16)]);
        }
        return sb.toString();
    }

    public static String md5(String input) throws Exception {
        return code(input, 32);
    }
    
    /**
    * md5 16位
    *
    * @Title: md5_16
    * @Description:
    * @param input
    * @return
    * @throws Exception String    返回类型
    * @throws
    * @author wanjian
    */
    public static String md5_16(String input) throws Exception {
        return code(input, 16);
    }

    public static String code(String input, int bit) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
            if (bit == 16)
                return bytesToHex(md.digest(input.getBytes("utf-8"))).substring(8, 24);
            return bytesToHex(md.digest(input.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("Could not found MD5 algorithm.", e);
        }
    }

    public static String md5_3(String b) throws Exception {
        MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
        byte[] a = md.digest(b.getBytes());
        a = md.digest(a);
        a = md.digest(a);

        return bytesToHex(a);
    }

    /**
     * md5常规加密
     * @param s
     * @return
     */
    public final static String MD5Digest(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

		'a', 'b', 'c', 'd', 'e', 'f' };

		try {

		byte[] strTemp = s.getBytes();

		//使用MD5创建MessageDigest对象

		MessageDigest mdTemp = MessageDigest.getInstance("MD5");

		mdTemp.update(strTemp);

		byte[] md = mdTemp.digest();

		int j = md.length;

		char str[] = new char[j * 2];

		int k = 0;

		for (int i = 0; i < j; i++) {

		byte b = md[i];

	    //System.out.println((int)b);

		//将没个数(int)b进行双字节加密

		str[k++] = hexDigits[b >> 4 & 0xf];

		str[k++] = hexDigits[b & 0xf];

		}

		return new String(str);

		} catch (Exception e) {
			return null;
			}
		}
    
    
   /* public static void main(String[] args) throws Exception {
    	String s = "KKL"+"11009"+"2016-08-15"+"KKL2017FFDs";
    	String res = md5(s);
    	System.out.println(res);
    	System.out.println(code(s, 16));
	}*/
}
