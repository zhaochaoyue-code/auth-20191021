package com.cmcc.v2x.dso.authcs.util;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class Test {

    private static Category logger = Category.getInstance(Test.class);
    private static Logger logger1 = Logger.getLogger(Test.class);

    public static void main(String[] args) throws Exception {

        logger1.info("你好============");

//        HttpClientUtil httpClientUtil = new HttpClientUtil();
//        httpClientUtil.

//                HttpClientResultAuth result = HttpClientUtils.doGet(" http://localhost:8080/asyn");
//                System.out.println(result);


        System.out.println("你看好\r\n"+"赵超越");
        test("20190924133049794","20190924153049794","20190924153049794");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        Date now = new Date();
//        Date afterDate = new Date(now .getTime() + 3600000);//600000十分钟
//        Date afterDate1 = new Date(now .getTime());
//        System.out.println(sdf.format(afterDate )+"============"+sdf.format(afterDate1));

//        calculateTimeDifferenceByDuration();
    }

    public static long test(String dbDate, String vailDate,String  beforedate) throws ParseException {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 *60;
        long nm = 1000 * 60;
        Date now = new Date();
        Date d3 = new Date(now .getTime());

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date d1 = df.parse(dbDate);
        Date d2 = df.parse(vailDate);
        Date d4 = df.parse(beforedate);

        Date afterDate = new Date(new Date().getTime() + 3600000);
//        String validate =df.format(afterDate);
        Date sysDate = new Date(new Date().getTime());
//        String sysdate =df.format(sysDate);
        Date beforeDate = new Date(new Date().getTime()+600000);
//        String beforedate =df.format(beforeDate);
//        long diff = d1.getTime() + d2.getTime()-d3.getTime()-d4.getTime();
//        请求时间 +超时时间-当前时间 -提前提起时间（15）
        long diff = d1.getTime() + afterDate.getTime()-sysDate.getTime()-beforeDate.getTime();

//        long day = diff/nd;
//        long hour = diff/nh;
        long minute = diff/nm;
        if(diff<0){//token 已过期
            return -1;
        }else{

            System.out.println("相差的分钟数为："+minute);
            return minute;
        }
//        log.info("相差的天数为："+day);
//        log.info("相差的小时为："+hour);
//        log.info("相差的分钟数为："+minute);
//        System.out.println("相差的天数为："+day);
//        System.out.println("相差的小时为："+hour);
//        System.out.println("相差的分钟数为："+minute);

    }

    public static void calculateTimeDifferenceByDuration() {
        Instant inst1 = Instant.now();  //当前的时间
        System.out.println("Inst1：" + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));     //当前时间+10秒后的时间
        System.out.println("Inst2：" + inst2);
        Instant inst3 = inst1.plus(Duration.ofDays(125));       //当前时间+125天后的时间
        System.out.println("inst3：" + inst3);

        System.out.println("以毫秒计的时间差：" + Duration.between(inst1, inst2).toMillis());

        System.out.println("以秒计的时间差：" + Duration.between(inst1, inst3).getSeconds());

    }
}
