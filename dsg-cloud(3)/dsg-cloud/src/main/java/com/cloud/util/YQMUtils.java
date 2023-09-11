package com.cloud.util;


import java.util.Arrays;
import java.util.List;
/**
*@Author Vibert
*@Description 4位数邀请码生成类
*@Date 2021/11/11 23:03
*/
public class YQMUtils {
    //private static final Logger logger = LoggerFactory.getLogger(YQMUtils.class);
    private final static List<String> list = Arrays.asList("0", "1","2","3","4","5","6","7","8","9"
            , "a","b","c","d","e","f","g","h","i"
            , "j","k","l","m","n","o","p","q","r"
            , "s","t","u","v","w","x","y","z");

    /**
     *
     * @param begin 默认为1
     * @param oldstr 最后一位邀请码
     * @return
     */
    public static String getString(int begin,String oldstr) {
        int end=0;
        String s =null;
        while (begin<30000){
            String a1;
            String a2;
            String a3;
            String a4;
            int i4 = (begin) % 36;
            int i3 = (begin/(36)) % 36;
            int i2 = (begin/(36*36)) % 36;
            int i1 = (begin/(36*36*36)) % 36;
            a1 = list.get(i1);
            a2 = list.get(i2);
            a3 = list.get(i3);
            a4 = list.get(i4);
            s = a1+a2+a3+a4;
            if(end==begin){
                return s;
            }
            begin++;
            if(s.equals(oldstr)){
                end = begin;
            }
        }
        return s;
    }
}
