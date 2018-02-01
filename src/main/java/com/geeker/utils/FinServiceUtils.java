package com.geeker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/11/2 0002.
 */
public class FinServiceUtils {
    public static String getFinId(){
        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String str1 = sdf1.format(date1);
        return str1+String.valueOf((int)((Math.random()*9+1)*100000));
    }
}
