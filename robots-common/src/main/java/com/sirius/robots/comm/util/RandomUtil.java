package com.sirius.robots.comm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 */
public class RandomUtil {

    /**
     * 生成多位随机数字
     * @return
     */
    public static String generateRandomNum(Integer num){
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        for(int i=0;i<num;i++) {
            randomStr.append(random.nextInt(10));
        }
        return randomStr.toString();
    }

    /**
     * 生成指定位数的随机数
     * @param length
     * @return
     */
    public static String getRandom(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }

}
