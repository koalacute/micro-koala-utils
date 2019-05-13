package com.koalacute.microkoala.utils.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     *  乘法
     */
    public static BigDecimal multiply(BigDecimal numOne, BigDecimal numTwo) {
        if (numOne == null || numTwo == null) {
            return BigDecimal.ZERO;
        }
        return numOne.multiply(numTwo);
    }

    /**
     * 多个累加格式金额
     */
    public static BigDecimal formatAddAmount(BigDecimal... decimal) {
        if (null == decimal) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = new BigDecimal(0);
        for (BigDecimal amount : decimal) {
            result = result.add(amount);
        }
        return result;

    }

    /**
     * 格式化金额
     * 
     * @param decimal
     * @return
     */
    public static String formatMoney(BigDecimal decimal) {
        decimal = (decimal == null) ? BigDecimal.ZERO : decimal;
        return formatMoney(decimal, 2);
    }

    /**
     * 乘法格式金额
     */
    public static String formatMultiplyMonty(BigDecimal decimal, BigDecimal decimalTwo) {
        return formatMoney(multiply(decimal, decimalTwo));
    }

    /**
     * 乘法格式金额
     */
    public static String formatMultiplyPercent(BigDecimal decimal, BigDecimal decimalTwo) {
        return formatPercent(multiply(decimal, decimalTwo));
    }

    /**
     * 格式化费率
     * 
     * @param decimal
     * @return
     */
    public static String formatPercent(BigDecimal decimal) {
        decimal = (decimal == null) ? BigDecimal.ZERO : decimal;
        return formatPercent(decimal, 2);
    }

    /**
     * 格式化费率
     * 
     * @param decimal
     * @return
     */
    public static String formatPercent(BigDecimal decimal, int scale) {
        return formatPercentNum(decimal, scale) + "%";
    }

    /**
     * 格式化百分比数字(不带百分号)
     * 
     * @param decimal
     * @return
     */
    public static String formatPercentNum(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "0.00";
        }
        BigDecimal num = decimal.multiply(new BigDecimal("100")).setScale(scale, RoundingMode.HALF_UP);
        return String.valueOf(num);
    }

    /**
     * 格式化数字
     * 
     * @param decimal
     * @param scale
     * @return
     */
    public static String formatMoney(BigDecimal decimal, int scale) {
        if (decimal == null) {
            return "0.00";
        }
        return String.valueOf(decimal.setScale(scale, RoundingMode.HALF_UP));
    }

    /**
     * 2015-08-24
     * 将金额转换为1,000 格式 
     * 
     * @param decimal
     * @return
     */
    public static String formatCommaMoney(BigDecimal decimal) {
        if (decimal == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("#,###");
        return String.valueOf(df.format(decimal));
    }

    /**
     * 
     * 判断字符串长度  <=255
     * @param s
     * @return
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    /**
     * 身份证号取男女
     * 
     * @param identityNo
     * @return
     */
    public static String getIdentityGender(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        Integer val = Integer.parseInt(String.valueOf(identityNo.charAt(16)));
        return (val % 2 == 0) ? "女" : "男";
    }

    
    /**
     * 身份证号取先生或女士
     * 
     * @param identityNo
     * @return
     */
    public static String getIdentityGenderCall(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        Integer val = Integer.parseInt(String.valueOf(identityNo.charAt(16)));
        return (val % 2 == 0) ? "女士" : "先生";
    }
    
    /**
     * 根据身份证号取年龄
     * 
     * @param identityNo
     * @return
     */
    public static Integer getIdentityAgeCall(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        String birthyear = identityNo.substring(6, 10);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");  
        String year=simpleDateFormat.format(new Date());  
        return Integer.parseInt(year)-Integer.parseInt(birthyear);
    }
    
    /**
     * 身份证号取生日
     * 
     * @param identityNo
     * @return
     */
    public static String getIdentityBirth(String identityNo) {
        if (StringUtils.isBlank(identityNo) || identityNo.length() != 18) {
            return null;
        }
        String birth = identityNo.substring(6, 14);
        return DateFormatUtil.string2string(birth, "yyyyMMdd", "yyyy-MM-dd");
    }
    /**
     * 根据身份证号码取年龄
     * @param identityNo
     * @return
     */
	public static int getAgeByIdentityNo(String identityNo) {
		String birthday = getIdentityBirth(identityNo);
		if(StringUtils.isBlank(birthday)){
			return 0;
		}
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(DateFormatUtil.string2date(birthday, "yyyy-MM-dd"));
		calEnd.setTime(new Date());
		int year = calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
		if (calBegin.get(Calendar.MONTH) == calEnd.get(Calendar.MONTH)) {
			if (calEnd.get(Calendar.DAY_OF_MONTH) < calBegin.get(Calendar.DAY_OF_MONTH)) {
				year -= 1;
			}
		} else if (calEnd.get(Calendar.MONTH) < calBegin.get(Calendar.MONTH)) {
			year -= 1;
		}
		return year;
	}
    /**
     * 查询参数
     * 
     * @param paramMap
     * @param name
     * @return String
     */
    public static String getValue(Map<String, Object> paramMap, String name) {
        if (paramMap == null || paramMap.isEmpty()) {
            return null;
        }
        if (!paramMap.containsKey(name)) {
            return null;
        }
        Object obj = paramMap.get(name);
        if (obj == null) {
            return null;
        }
        String value = obj.toString();
        return value.trim();
    }

    public static String getValue(Map<String, Object> paramMap, String name, String def) {
        String value = getValue(paramMap, name);
        return StringUtils.isBlank(value) ? def : value;
    }

    /**
     * 获取Integer的字符串类型
     * 
     * @param value
     * @return
     */
    public static String getValue(Integer value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 获取Integer的字符串类型
     * 
     * @param value
     * @return
     */
    public static String getValue(BigDecimal value) {
        if (value == null) {
            return String.valueOf(BigDecimal.ZERO);
        }
        return String.valueOf(value);
    }

    /**
     * 获取Long的字符串
     * 
     * @param value
     * @return
     */
    public static String getValue(Long value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 获取String的trimValue
     * 
     * @param value
     * @return
     */
    public static String getValue(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.trim();
    }

    /**
     * 获取Decimal默认值
     * 
     * @param applyAmount
     * @return
     */
    public static BigDecimal getDecimal(BigDecimal applyAmount) {
        return applyAmount == null ? BigDecimal.ZERO : applyAmount;
    }

    /**
     * 
     * String to BigDecimal
     * @param str
     * @return
     */
    public static BigDecimal getDecimal(String str) {
        if (!StringUtils.isNumeric(str)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(str);
    }

    /**
     * 如果字符串为空返回默认值
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getValue(String value, String defaultValue) {
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    /**
     * 截取尾數字符
     * 
     * @param value
     * @param len
     * @return
     */
    public static String getLast(String value, int len) {
        if (StringUtils.isBlank(value) || len <= 0 || value.length() - len <= 0) {
            return getValue(value);
        }
        return value.substring(value.length() - len, value.length());
    }

    /**
     * <pre>
     * 字符串替换显示*
     * 
     * 比如身份证要显示成：370************013
     * 
     * @param value   - 要操作的内容
     * @param start   - 替换开始位置
     * @param end     - 替换结束位置
     * @param repChar - 要替换成的内容
     * @return String
     * </pre>
     */
    public static String replace(String value, int start, int end, String repChar) {
        if (StringUtils.isBlank(value) || start > end || repChar == null || value.length() < end) {
            return value;
        }
        String con = value.substring(start, end);
        String repStr = StringUtils.repeat(repChar, con.length());
        return value.replace(con, repStr);
    }

    /**
     * 往对象里设置属性值
     * 
     * @param bean
     * @param name
     * @param value
     */
    public static void setStringValue(Object bean, String name, String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("没有对应的属性", e);
        }
    }

    /**
     * 往对象里设置值
     * 
     * @param paramMap
     * @param bean
     * @param paramName
     * @param fieldName
     */
    public static void setStringValue(Map<String, Object> paramMap, Object bean, String paramName, String fieldName) {
        String value = CommonUtils.getValue(paramMap, paramName);
        setStringValue(bean, fieldName, value);
    }

    public static void setStringValue(Map<String, Object> paramMap, Object bean, String fieldName) {
        String value = CommonUtils.getValue(paramMap, fieldName);
        setStringValue(bean, fieldName, value);
    }

    /**
     * 计算百分比
     * 
     * @param numOne
     * @param numTwo
     * @return
     */
    public static String calPercent(long numOne, long numTwo, int scale) {
        if (numTwo == 0) {
            return String.valueOf(BigDecimal.ZERO.setScale(scale)) + "%";
        }
        BigDecimal start = BigDecimal.valueOf(numOne);
        BigDecimal end = BigDecimal.valueOf(numTwo);
        BigDecimal result = start.multiply(new BigDecimal(100)).divide(end, RoundingMode.HALF_UP).setScale(scale);
        return String.valueOf(result) + "%";
    }

    /**
     * 是否小于等于
     * 
     * @param valOne
     * @param valTwo
     * @return boolean
     */
    public static boolean isLessEqThan(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.compareTo(valueTwo) <= 0;
    }

    public static boolean isLessThan(BigDecimal valOne, BigDecimal valTwo) {
        BigDecimal valueOne = getDecimal(valOne);
        BigDecimal valueTwo = getDecimal(valTwo);
        return valueOne.compareTo(valueTwo) < 0;
    }

    public static boolean isGtZero(BigDecimal valOne) {
        BigDecimal valueOne = getDecimal(valOne);
        return valueOne.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否包含
     * 
     * @param value
     * @param chars
     * @return boolean
     */
    public static boolean contains(String value, String chars) {
        if (StringUtils.isAnyBlank(value, chars)) {
            return false;
        }
        return value.indexOf(chars) != -1;
    }

    /**
     * 是否包含任一
     * 
     * @param value
     * @param chars
     * @return
     */
    public static boolean containsAny(String value, String... chars) {
        if (chars == null || chars.length == 0) {
            return false;
        }
        boolean result = false;
        for (String content : chars) {
            if (contains(value, content)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 判断当前身份证是否已关键数字开头
     * 
     * @param value
     * @param words
     * @return
     */
    public static boolean startWithWords(String value, String[] words){
    	if(StringUtils.isBlank(value)){
    		return false;
    	}
    	 boolean result = false;
         for (String content : words) {
             if (value.startsWith(content)) {
                 result = true;
                 break;
             }
         }
         return result;
    }
    /**
     * Get Int
     * 
     * @param value
     * @return
     */
    public static Integer getInt(String value) {
        if (!StringUtils.isNumeric(value)) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * 随机码
     * 
     * @return String
     */
    public static String getRandom() {
        return String.valueOf(Math.random());
    }

 
    /**
     * 是否为零
     * 
     * @param value
     * @return boolean
     */
    public static final boolean isZero(BigDecimal value) {
        return value == null || BigDecimal.ZERO.compareTo(value) == 0;    
    }

    /**
     * 字符串左补齐
     * 
     * @param value
     * @param length
     * @param sign
     * @return
     */
    public static String leftPad(String value, int length, String sign) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (value.trim().length() >= length) {
            return value;
        }
        int padLen = length - value.trim().length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < padLen; i++) {
            builder.append(sign);
        }
        return builder.toString() + value;
    }

    public static final List<String> split(String content, String sign) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(sign)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(content.trim().split(sign));
    }

    /**
     * 身份证前4位拼接城市地区
     * 
     * @param identityNo
     * @return String
     */
    public static final String getIdentityArea(String identityNo) {
        if (StringUtils.isBlank(identityNo)) {
            return null;
        }
        return identityNo.substring(0, 4) + "00";
    }

    /**
     * 是否本地籍
     * 
     * @param workCity
     * @param code
     * @return
     */
    public static final String localRegistry(String workCity, String identityNo) {
        if (StringUtils.isAnyBlank(workCity, identityNo)) {
            return "feibendiji";
        }
        String code = CommonUtils.getIdentityArea(identityNo);
        return StringUtils.equals(workCity, code) ? "bendiji" : "feibendiji";
    }

    /**
     * 是否本地籍(新)
     * 
     * @param workCity
     * @param code
     * @return
     */
    public static final String newLocalRegistry(String wholeCode, String identityNo) {
        if (StringUtils.isAnyBlank(wholeCode, identityNo)) {
            return "feibendiji";
        }
        String code = CommonUtils.getIdentityArea(identityNo);
        return wholeCode.contains(code) ? "bendiji" : "feibendiji";
    }

    /**
     * 是否本地籍
     * 
     * @param workCity
     * @param code
     * @return
     */
    public static final String leftPadNum(Integer num) {
        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    public static boolean isNeBlankAndDef(String val, String def) {
        return StringUtils.isNotBlank(val) && !StringUtils.equals(val, def);
    }

    /**
    * @Title: trimMap
    * @Description:
    * @param map 
    * @return  void    返回类型
    * @throws
    * @author panzongwei 
    */
    public static void trimMap(Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<String, Object>();
            return;
        }
        for (Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                map.put(entry.getKey(), entry.getValue().toString().trim());
            }
        }
    }

    /**
     * 根据字符串数组判断一句话中是否含有数据中的数据
     * @return
     */
    public static boolean isContent(String str, String[] strList) {
        boolean flag = false;
        if (null == str || "".equals(str)) {
            return false;
        }
        for (String content : strList) {
            if (content.equals(str)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
}

/**
* 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
* @param nonCheckCodeCardId
* @return
*/
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
            || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
    
    public static int getRandomResult(){
//        Random r = new Random();
//        int i = r.nextInt(3) + 1;
//        if(i > 1){
//            return 1;
//        }else{
//            return 2;
//        }
        return 1;
    }
    
    
    /**
     * 验证信用卡号合法性
     * @param cardNo
     * @return 合法为true，否则为flase;
     */
    public static boolean confirmCreditCardLegality(String cardNo){
        boolean flag = true;
        String[] blackList = {"6253624"};
        if(StringUtils.isBlank(cardNo)){
            flag = false;
        }else{
            for(String blackNo : blackList){
                if(cardNo.startsWith(blackNo)){
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }


    public static List<NameValuePair> getNameValuePairList(Object obj){
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for(int i = 0;i < declaredFields.length;i++){
                declaredFields[i].setAccessible(true);//允许获取私有属性的值
                if(declaredFields[i].get(obj) != null){
                    list.add(new BasicNameValuePair(declaredFields[i].getName(), declaredFields[i].get(obj).toString()));
                }
            }
        } catch (Exception e) {
            logger.error("getNameValuePairList 异常："+e.getMessage(),e);
        }
        return list;
    }


    /**
     * 对象转换为Map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, String> castObjectToMap(Object obj) throws Exception{
        Map<String, String> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for(int i = 0;i < declaredFields.length;i++){
            declaredFields[i].setAccessible(true);//允许获取私有属性的值
            if(declaredFields[i].get(obj) != null){
                map.put(declaredFields[i].getName(), declaredFields[i].get(obj).toString());
            }
        }
        return map;
    }

    public static Map<String, Object> castObjectToMap2(Object obj) throws Exception{
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for(int i = 0;i < declaredFields.length;i++){
            declaredFields[i].setAccessible(true);//允许获取私有属性的值
            if(declaredFields[i].get(obj) != null){
                map.put(declaredFields[i].getName(), declaredFields[i].get(obj));
            }
        }
        return map;
    }

    /**
     * 把对象中null值的String属性赋值为空的字符串""
     * @param src
     * @param <T>
     * @return
     */
    public static <T> T nullValueToEmptyString(T src){
        try {
            if (src != null){
                Field[] fields = src.getClass().getDeclaredFields();
                for (Field field : fields){
                    if (field.getType() == String.class){
                        field.setAccessible(true);
                        Object value = field.get(src);
                        if (value == null){
                            field.set(src, StringUtils.EMPTY);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return src;
    }

    public static String sortAndMd5(String... s) throws Exception {
        Arrays.sort(s);
        StringBuilder str = new StringBuilder();
        Arrays.asList(s).stream().forEach(a -> str.append(a));
        return MD5Utils.encrypt(str.toString());
    }

    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

}
