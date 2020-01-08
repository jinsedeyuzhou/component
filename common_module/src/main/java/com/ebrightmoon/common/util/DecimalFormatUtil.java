package com.ebrightmoon.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Time: 2020-01-04
 * Author:wyy
 * Description:
 */
public class DecimalFormatUtil {

    private DecimalFormatUtil() {
        /* cannot be instantiated */
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
        DecimalFormat df = new DecimalFormat("##.##%");
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
        DecimalFormat df = new DecimalFormat("##.00%");
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
        DecimalFormat df = new DecimalFormat("##%");
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
     * 单位转换(万)
     *
     * @return
     */
    public static String toUnit(double number) {
        if (number >= 10000) {
            BigDecimal b = new BigDecimal(number / 10000);
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
        } else if (number == 0) {
            return "";
        } else {
            return number + "";
        }
    }

    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String toUnitNo(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(b) + "万";
    }


    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String toDana(int number) {
        if (number >= 10000) {
            BigDecimal b = new BigDecimal(number / 10000);
            return b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + "万";
        } else if (number == 0) {
            return "";
        } else {
            return number + "";
        }
    }

    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String toDana2(int number) {
        if (number >= 10000) {
            BigDecimal b = new BigDecimal(number / 10000.00);
            DecimalFormat df = new DecimalFormat("##0.00");
            return df.format(b.doubleValue())+ "万";
        } else if (number == 0) {
            return "";
        } else {
            return number + "";
        }
    }
    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String toUnitString(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "(万)、";
    }


    public static String formatPercent2(double d) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(d);
    }

    public static String formatPercent4(double d) {
        DecimalFormat df = new DecimalFormat("##0.0000");
        return df.format(d);
    }

    public static String formatPercent6(double d) {
        DecimalFormat df = new DecimalFormat("##");
        return df.format(d);
    }

    public static String formatPercent5(double d) {
        DecimalFormat df = new DecimalFormat("##0.00");
        return df.format(d);
    }

    public static String formatPercent8(int d) {
        DecimalFormat df = new DecimalFormat("00");
        return df.format(d);
    }

    public static String formatPercent9(double d) {
        DecimalFormat df = new DecimalFormat("#");
        return df.format(d);
    }

    public static String doubleToString(String number) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
    }

    public static String doubleToString2(double number) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        return df.format(number);
    }

    public static String doubleToString2(String number) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        return df.format(number);
    }


    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String doubleToString(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
    }

    /**
     * 单位转换(万)
     *
     * @return
     */
    public static String doubleToString1(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
    }

    /**
     * 单位转换(万)
     * 直接保留一位小数,不进行四舍五入
     *
     * @return
     */
    public static String doubleToString_No_Round(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        return b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue() + "";
    }


    /**
     * double四舍五入
     *
     * @return
     */
    public static int doubleToInt(double number) {
        BigDecimal b = new BigDecimal(number);
        b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        return b.intValue();
    }

    /**
     * double四舍五入
     *
     * @return
     */
    public static String StringToInts(double number) {
        BigDecimal b = new BigDecimal(number);
        b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.format("%1.0f", b);
    }

    /**
     * double四舍五入
     * digits  指定保留的位数
     *
     * @return
     */
    public static String StringToInts(String number, int digits) {
        BigDecimal b = new BigDecimal(number);
        b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.format("%1." + digits + "f", b);
    }

    /**
     * double四舍五入
     *
     * @return
     */
    public static String doubleToInts(double number) {
        BigDecimal b = new BigDecimal(number);
        b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.format("%1.0f", b);
    }

    /**
     * 单位转换(万),省略小数点，直接保留整数
     *
     * @return
     */
    public static String doubleToIntString(double number) {
        BigDecimal b = new BigDecimal(number / 10000);
        return b.intValue() + "";
    }

}
