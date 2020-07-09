package com.ebrightmoon.common.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java判断字符串是否为数字或中文或字母
 *
 * @author Administrator
 */
public class StringUtils {

    private static final String TAG = StringUtils.class.getSimpleName();

    private StringUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 格式化输入百分数字符 如果位数不够可采用下面的方法 DecimalFormat percentFormat = new
     * DecimalFormat("##.00%");有两位小数显示两位小数，不填0
     *
     * @param d
     * @return
     */
    public static String formatPercent(double d) {
        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(d);
    }

    /**
     * 格式化输入百分数字符 如果位数不够可采用下面的方法 DecimalFormat percentFormat = new
     * DecimalFormat("##.00%");保留两位小数，不足填0；
     *
     * @param d
     * @return
     */
    public static String formatPercent1(double d) {
        DecimalFormat df = new DecimalFormat("##.00");
        return df.format(d);
    }


    /**
     * 格式化输入百分数字符 如果位数不够可采用下面的方法 DecimalFormat percentFormat = new
     * DecimalFormat("##%");四舍五入保留整数
     *
     * @param d
     * @return
     */
    public static String formatPercent0(double d) {
        DecimalFormat df = new DecimalFormat("##");
        return df.format(d);
    }

    /**
     * 格式化输出本地货币字符
     *
     * @param d
     * @return
     */
    public static String formatLocalCurrency(double d) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String result = currencyFormat.format(d);
        return result.substring(1);
    }

    /**
     * 1.判断字符串是否仅为数字:
     *
     * @param str
     * @return
     */

    public static boolean isNumeric1(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用正则表达式 判断字符串是否仅为数字:
     *
     * @param str
     * @return
     */
    public static boolean isNumeric2(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 查找xml下第一个出现的关键值
     *
     * @param xml  字符源
     * @param name key值
     */
    public String getXmlValue(String xml, String name) {

        int beginIndex = xml.indexOf("<" + name + ">");
        int endIndex = xml.indexOf("</" + name + ">");
        if (beginIndex == -1 || endIndex == -1) {
            return null;
        }
        String retsult = xml
                .substring(beginIndex + name.length() + 2, endIndex);
        return retsult;
    }

    /**
     * 用ascii码 判断字符串是否仅为数字:
     *
     * @param str
     * @return
     */
    public static boolean isNumeric3(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    /**
     * 2.判断一个字符串的首字符是否为字母
     *
     * @param s
     * @return
     */
    public static boolean test(String s) {
        char c = s.charAt(0);
        int i = c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 3 .判断是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean vd(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40
                        && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * sun
     *
     * @param cTime
     * @param format "yyyy/MM/dd HH:mm"
     * @return
     */
    public static String formatUnixTime(long cTime, String format) {
        // Timestamp time = new Timestamp(cTime);
        SimpleDateFormat formatString = new SimpleDateFormat(format);
        return formatString.format(new Date(cTime));
    }

    /**
     * 获取随机串
     *
     * @return
     */
    public static String getRandomString() {
        StringBuffer result = new StringBuffer();
        result.append(String.valueOf((int) (Math.random() * 10))).append("_");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        result.append(str);

        return result.toString();
    }


    /**
     * 舍入
     * @param d
     * @return
     */
    public static String formatPercent7(double d) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        double setScale = bd.setScale(2, bd.ROUND_DOWN).doubleValue();
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(setScale);
    }

    /**
     * 格式化输出本地货币字符 减少.00
     *
     * @param d
     * @return
     */
    public static String formatLocalCurrency02(double d) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String result = currencyFormat.format(d);
        String moneyString = result.substring(1);

        return moneyString.substring(0, moneyString.length() - 3);
    }

    /**
     * 检查 URL 是否合法
     *
     * @param url
     * @return true 合法，false 非法
     */
    public static boolean isNetworkUrl(String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern patt = Pattern.compile(regex);
        Matcher matcher = patt.matcher(url);
        return matcher.matches();
    }

    /**
     * DecimalFormat Double类型转成固定格式如“0.0”
     *
     * @return
     */
    public static String getTextforDecimalFormat(Double d, String str) {
        DecimalFormat df = new DecimalFormat(str);
        return df.format(d);
    }

    /**
     * 获取Double对象
     */
    public static Double getDouble(String str) {
        Double mDouble = Double.valueOf(str);
        return mDouble;
    }

    /**
     * 日期转为格林威治时间
     */
    public static long getdaytime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dt2.getTime();
    }

    /**
     * 把字符串MD5
     *
     * @param original
     * @return
     */
    public static String generateMD5(String original) {
        try {
            LogUtils.d(TAG, "before MD5 " + original);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            LogUtils.d(TAG, "after MD5 " + sb.toString());
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

}
