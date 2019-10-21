package com.cmcc.v2x.dso.authcs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) {


        String a = "201910481018175148804";
        String b = "201910481018165148804";
        comparetodate(a,b);


//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        String requestime = new SimpleDateFormat("yyyyMsMddHHmmsSSS").format(new Date());

        System.out.println(requestime);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
//        String requestime =  sdf.format(new Date());
//        Date afterDate = new Date(new Date().getTime() + 3600000);//一小时
//        String validate =sdf.format(afterDate);
//        Date sysDate = new Date(new Date().getTime());
//        String sysdate =sdf.format(sysDate);
//        Date beforeDate = new Date(new Date().getTime()+600000);//十分钟
//        String beforedate =sdf.format(beforeDate);
//        System.out.println(requestime+"================"+sysdate+"================="+validate+"========================"+beforedate);
    }

    private static boolean comparetodate(String requestime, String systime) {
        int result=requestime.compareTo(systime);
        if (result==0)
            System.out.print("a=b");
        else if (result < 0 )
            System.out.print("a<b");
        else System.out.print("a>b");
        return  true;
    }
}
