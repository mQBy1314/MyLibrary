package com.mqby.mqlibrary.tools;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author MaQiang
 * @time 2018/6/11 0011 18:35
 * @QQ 1033785970
 * @class 数字工具类
 */
public class NumTool {


    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    /**
     * 将整数转换成汉字数字
     *
     * @param num 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatInteger(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.parseInt(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    continue;
                } else {
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /**
     * 将小数转换成汉字数字
     *
     * @param decimal 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatDecimal(double decimal) {

        String decimals = String.valueOf(decimal);
        return formatDecimal(decimals);
    }

    public static String formatDecimal(String decimal) {
        String result;
        int decIndex = decimal.indexOf(".");

        if (decIndex >= 0) {
            int integ = Integer.parseInt(decimal.substring(0, decIndex));
            String xiaoshu = decimal.substring(decIndex + 1);
            if (xiaoshu.length() > 0) {
                int dec = Integer.valueOf(xiaoshu);
                result = formatInteger(integ) + "点" + formatFractionalPart(dec);
            } else {
                result = formatInteger(integ);
            }

        } else {
            int integ = Integer.parseInt(decimal);
            result = formatInteger(integ);
        }

        return result;
    }

    /**
     * 格式化小数部分的数字
     *
     * @param decimal 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int n = Integer.parseInt(val[i] + "");
            sb.append(numArray[n]);
        }
        return sb.toString();
    }

    /**
     * 去除科学记数法 2016.00-->2016
     */
    public static String convertDouble(String double1) {
        if (TextUtils.isEmpty(double1)) {
            return double1;
        }
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        try {
            return nf.format(Double.valueOf(double1));
        } catch (Exception e) {
            return double1;
        }

    }

    /**
     * 去除科学记数法 2016.3-->2,016.30
     */
    public static String convertDouble(Double double1) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置
        return decimalFormat.format(double1);
    }

    /**
     * 对double数据进行取精度.
     *
     * @param value        double数据.
     * @param scale        精度位数(保留的小数位数).
     * @param roundingMode 精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale,
                               int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

    public static double round(double value, int scale) {
        return round(value, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }


    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    public static String mulToString(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return convertDouble(bd1.multiply(bd2).toPlainString());
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理

        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide
                (bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
