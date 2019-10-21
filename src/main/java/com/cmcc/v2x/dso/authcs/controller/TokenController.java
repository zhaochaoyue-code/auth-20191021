package com.cmcc.v2x.dso.authcs.controller;

import com.cmcc.v2x.dso.authcs.model.Auth_token;
import com.cmcc.v2x.dso.authcs.model.Obu_data;
import com.cmcc.v2x.dso.authcs.model.Respcode;
import com.cmcc.v2x.dso.authcs.service.ObudataService;
import com.cmcc.v2x.dso.authcs.service.TokenService;
import com.cmcc.v2x.dso.authcs.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    private static Logger logger = Logger.getLogger(KeyController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObudataService obudataService;

    @RequestMapping(value = "/token")
    public Object token(int carid,int type,String hashInfo) throws Exception {
        //请求时间 过期时间 将要过期时间
        Respcode code = new Respcode();
        Auth_token token = new Auth_token();
        Map<Date, String> datemap = getdate();
//        String requestime = datemap.get("reqDate");
        String requestime = new SimpleDateFormat("yyyyMsMddHHmmsSSS").format(new Date());
        String ueMAC = "";
        Obu_data obu_data = obudataService.getKeyByID(carid);
        if (obu_data != null) {
            ueMAC = obu_data.getObuMac();
        } else {
            code.setCode(Respcode.MAC_CODE);
            code.setMessage(Respcode.MAC_MES);
            return code;
        }
//        String authdata = MD5Util.getMD5Str(carid + ueMAC + requestime);
        String authdata ="123";

        if (!hashInfo.equals(authdata)) {//AB281E7556CA2945
            code.setCode(Respcode.HASHERROR_CODE);
            code.setMessage(Respcode.HASHERROR_MES);
            return code;
        }
//            下面写业务
        Object obj = tokenService.getObj(carid,requestime,ueMAC);
        System.out.println(obj);
        return obj;

    }


    public Map<Date,String> getdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        Map<Date,String> map = new HashMap<Date,String>();

        Date afterDate = new Date(new Date().getTime() + 3600000);
        Date sysDate = new Date(new Date().getTime());
        Date reqDate = new Date(new Date().getTime());
        Date beforeDate = new Date(new Date().getTime()+600000);
        String sysdate =sdf.format(sysDate);
        String requestime =  sdf.format(reqDate);
        String validate =sdf.format(afterDate);
        String beforedate =sdf.format(beforeDate);
        map.put(sysDate,sysdate);
        map.put(reqDate,requestime);
        map.put(afterDate,validate);
        map.put(beforeDate,beforedate);
        return map;
    }
}
