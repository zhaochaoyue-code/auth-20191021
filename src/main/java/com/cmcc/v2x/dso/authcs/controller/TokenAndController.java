package com.cmcc.v2x.dso.authcs.controller;


import com.cmcc.v2x.dso.authcs.model.*;
import com.cmcc.v2x.dso.authcs.service.KeyService;
import com.cmcc.v2x.dso.authcs.service.ObudataService;
import com.cmcc.v2x.dso.authcs.service.TokenService;
import com.cmcc.v2x.dso.authcs.util.EncryptionUtil;
import com.cmcc.v2x.dso.authcs.util.MD5Util;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
@RestController
public class TokenAndController {
    private static Logger logger = Logger.getLogger(TokenAndController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KeyService keyService;

    @Autowired
    private ObudataService obudataService;

    @RequestMapping(value = "/tandk")
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
        String authdata ="123";

        if (!hashInfo.equals(authdata)) {
            code.setCode(Respcode.HASHERROR_CODE);
            code.setMessage(Respcode.HASHERROR_MES);
            return code;
        }
//            下面写业务
        Respcode respcode  = tokenService.getAllObj(carid,requestime,ueMAC);
        System.out.println(respcode);
        Object other = null;

        if(respcode.getCode() !=10507){
        other = tokenService.other(carid, requestime, ueMAC);
        }
        return other;

    }

    public Map<Date,String> getdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        Map<Date,String> map = new HashMap<Date,String>();

        Date sysDate = new Date(new Date().getTime());
        Date reqDate = new Date(new Date().getTime());
        Date afterDate = new Date(sysDate.getTime() + 3600000);
        Date beforeDate = new Date(sysDate.getTime()+600000);
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

