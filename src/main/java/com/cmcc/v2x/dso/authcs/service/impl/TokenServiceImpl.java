package com.cmcc.v2x.dso.authcs.service.impl;

import com.cmcc.v2x.dso.authcs.mapper.Auth_keyMapper;
import com.cmcc.v2x.dso.authcs.mapper.Auth_keylogMapper;
import com.cmcc.v2x.dso.authcs.mapper.Auth_tokenMapper;
import com.cmcc.v2x.dso.authcs.mapper.Auth_tokenlogMapper;
import com.cmcc.v2x.dso.authcs.model.*;
import com.cmcc.v2x.dso.authcs.service.TokenService;
import com.cmcc.v2x.dso.authcs.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.Exceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private Auth_tokenMapper tokenMapper;

    @Autowired
    private Auth_tokenlogMapper tokenlogMapper;

    @Autowired
    private Auth_keyMapper keyMapper;

    @Autowired
    private Auth_keylogMapper keylogMapper;


    @Transactional
    @Override
    public Object getObj(int carid,String requestime,String mac) throws Exception {
        Respcode code = new Respcode();
        Result resultObj = new Result();
        Auth_token auth_token = tokenMapper.selectByPrimaryKey(carid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        Auth_token tokenObj = new Auth_token();
        int returnresult =0;
        try {
        if (auth_token == null) {
            tokenObj.setCarid(carid);
            tokenObj.setMac(mac);
            tokenObj.setStatus("初始");
            tokenObj.setVersion(0);
            tokenObj.setTimestamp(sdf.parse(requestime));
            tokenObj.setAuthtoken(UUID.randomUUID().toString());
            int result = tokenMapper.insert(tokenObj);
            if(result<1) {
                code.setCode(10501);
                code.setMessage(Respcode.ERROR_MES);
                return code;
            }

            //auth处理完成转发给v2x
            Auth_token auth_tokenfin = tokenMapper.selectByPrimaryKey(carid);
            if(auth_tokenfin !=null){
                // id token validtime timestamp
                String id =String.valueOf(auth_tokenfin.getCarid());
                String token =auth_tokenfin.getAuthtoken();
//                String validtime =auth_tokenfin.get
                String timestamp =sdf.format(auth_tokenfin.getTimestamp());
                RestTemplate restTemplate = new RestTemplate();
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("token",token);
                params.put("timestamp",timestamp);
                String url  ="http://localhost:8080/test";
                ResponseEntity<String> response = restTemplate.postForEntity( url, params , String.class );
                System.out.println(response.getBody());
                String mssage = response.getBody();
                JSONObject obj = JSONObject.fromObject(mssage);
                if (mssage.length() == 0) {
                    code.setCode(10505);
                    code.setMessage(Respcode.NOBACK_MES);
                    return code;
                }
                returnresult = obj.getInt("result");
                String returntimestamp = obj.getString("timestamp");

                if(returnresult==0){//成功

                    //v2x收到信息返回成功auth 更新token表状态为处理完成
                    tokenObj.setStatus("处理完成");
                    tokenObj.setCarid(carid);
                    tokenObj.setVersion(0);
        //          int i =tokenService.updateToken(token);
                    int i = tokenMapper.updateByKeyAndStatus(tokenObj);
                    if(i<1) {
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }

                    //拿到数据
                    //调用AES工具类把拿到的数据进行加密
                    String password =  EncryptionUtil.encrypt(token);
                    resultObj.setToken(password);
                    resultObj.setTokenvalidtime(returntimestamp);
                    resultObj.setResult(returnresult);
                    resultObj.setTokenvalidtime(returntimestamp);
                    return  resultObj;
                }else if(returnresult==1){//终端不合法
                    throw new Exception("终端不合法");
                }else{//报文不能识别
                    throw new Exception("报文不能识别");
                }

            }
        }else{
            String status = auth_token.getStatus();
            Date date = auth_token.getTimestamp();
            String authtoken = auth_token.getAuthtoken();
            int  car = auth_token.getCarid();
            String  ma = auth_token.getMac();
            int ver = auth_token.getVersion();
            String reqtime =sdf.format(auth_token.getTimestamp());
            long checktime =  checktime(reqtime);
            if (checktime < 0 && (auth_token.getStatus()).equals("处理完成")) {

                int a = tokenMapper.deleteByPrimaryKey(carid);
                if(a<1){
                    code.setCode(10501);
                    code.setMessage(Respcode.ERROR_MES);
                    return code;
                }
                Auth_tokenlog authTokenlog = new Auth_tokenlog();
                authTokenlog.setAuthtoken(authtoken);
                authTokenlog.setCarid(car);
                authTokenlog.setTimestamp(date);
                authTokenlog.setMac(ma);
                authTokenlog.setVersion(ver);
                authTokenlog.setStatus(status);
                int b = tokenlogMapper.insertSelective(authTokenlog);
                if(b<1){
                    code.setCode(10501);
                    code.setMessage(Respcode.ERROR_MES);
                    return code;
                }

                Auth_token atoken = new Auth_token();
                atoken.setCarid(carid);
                atoken.setMac(mac);
                atoken.setStatus("初始");
                atoken.setVersion(0);
                atoken.setTimestamp(sdf.parse(requestime));
                atoken.setAuthtoken(UUID.randomUUID().toString());

                int c = tokenMapper.insertSelective(atoken); //---->走一遍流程
                if(c<1){
                    code.setCode(10501);
                    code.setMessage(Respcode.ERROR_MES);
                    return code;
                }
//                int result = tokenService.deleteToken(carid);
//                //新增tokelog
//                int a = tokenService.insertToken(token);
            }

            if(checktime > 0  && (auth_token.getStatus()).equals("处理完成")){
                code.setCode(10201);
                String timeMes = "已请求token "+Respcode.TIME_MES+checktime+"毫秒";
                code.setMessage(timeMes);
                return code;
            }

        }
            }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if(returnresult==1){//不合法
                code.setCode(10503);
                code.setMessage(Respcode.NO_MES);
                return code;
            }else if(returnresult==2) {//不识别
                code.setCode(10504);
                code.setMessage(Respcode.NOTKNOW_MES);
                return code;
            }
        }

        return  code;
    }

    @Transactional
    @Override
    public Respcode getAllObj(int carid, String requestime, String mac) throws Exception {
        Respcode code = new Respcode();
        Result resultObj = new Result();
        Auth_token auth_token = tokenMapper.selectByPrimaryKey(carid);
        Auth_key auth_key = keyMapper.selectByPrimaryKey(carid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        String systime = sdf.format(new Date());
        Auth_token tokenObj = new Auth_token();
        Auth_key keyObj = new Auth_key();
        int returnresult =0;
        //第一种情况 都为空
        if(auth_token ==null && auth_key==null){
          return tokenandkeyisnull(carid, requestime, mac);
        }

        //第三种情况
        if(auth_token !=null && auth_key!=null &&(auth_token.getStatus()).equals("处理完成") &&(auth_key.getStatus()).equals("处理完成")){

            return code;
            /*String id =String.valueOf(auth_token.getCarid());
            String token =auth_token.getAuthtoken();
            String key = auth_key.getAuthkey();
            String tokenvalidtime =sdf.format(auth_token.getTimestamp());
            String keyvalidtime =sdf.format(auth_key.getTimestamp());

            String password =  EncryptionUtil.encrypt(token);
            resultObj.setToken(password);
            resultObj.setKey(EncryptionUtil.encrypt(key));
            resultObj.setTokenvalidtime(tokenvalidtime);
            resultObj.setKeyvalidtime(keyvalidtime);
            resultObj.setResult(returnresult);
            resultObj.setRandomnum((int)(Math.random()*100+1));//1到100随机数
            return  resultObj;*/
        }

        //第四种情况 有记录 没过期，也没有快过期  状态  初始
        if(auth_token !=null && auth_key!=null &&(auth_token.getStatus()).equals("初始") &&(auth_token.getStatus()).equals("初始")){
            //再次发送http请求  超时时间 查出来时间计算
            code.setCode(10507);
            code.setMessage(Respcode.REQ_MES);
            return code;

        }

        //第二种情况 2.都有值 时间问题  要过期 和快过期 备份数据情况1
        if(auth_token !=null && auth_key!=null && comparetodate(requestime,systime)){
            String tokentime =sdf.format(auth_token.getTimestamp());
            String keytime = sdf.format(auth_key.getTimestamp());
            comparetodate(requestime,systime);
            String status = auth_token.getStatus();
            String status1 = auth_key.getStatus();
            Date date = auth_token.getTimestamp();
            Date date1 = auth_key.getTimestamp();
            String authtoken = auth_token.getAuthtoken();
            String authkey = auth_key.getAuthkey();
            int  car = auth_token.getCarid();
            int  car1 = auth_key.getCarid();
            String  ma = auth_token.getMac();
            String  ma1 = auth_key.getMac();
            int ver = auth_token.getVersion();
            int ver1 = auth_key.getVersion();
            String reqtime =sdf.format(auth_token.getTimestamp());
            long checktime =  checktime(reqtime);
//            long checktime =  -1;
                if(checktime < 0){//已过期


                    int a = tokenMapper.deleteByPrimaryKey(carid);
                    int a1 = keyMapper.deleteByPrimaryKey(carid);
                    if(a<1|| a1<1){
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }
                    Auth_tokenlog authTokenlog = new Auth_tokenlog();
                    Auth_keylog authKeylog = new Auth_keylog();

                    authTokenlog.setAuthtoken(authtoken);
                    authTokenlog.setCarid(car);
                    authTokenlog.setTimestamp(date);
                    authTokenlog.setMac(ma);
                    authTokenlog.setVersion(ver);
                    authTokenlog.setStatus(status);

                    authKeylog.setAuthkey(authkey);
                    authKeylog.setCarid(car1);
                    authKeylog.setTimestamp(date1);
                    authKeylog.setMac(ma1);
                    authKeylog.setVersion(ver1);
                    authKeylog.setStatus(status1);

                    int b = tokenlogMapper.insertSelective(authTokenlog);
                    int b1 = keylogMapper.insertSelective(authKeylog);
                    if(b<1 || b1<1){
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }
                    return tokenandkeyisnull(carid, requestime, mac);
                }else{//还未过期
                    code.setCode(10201);
                    String timeMes = "已请求token "+Respcode.TIME_MES+checktime+"毫秒";
                    code.setMessage(timeMes);
                    return code;
                }
        }

        return code;
    }

    private boolean comparetodate(String requestime, String systime) {
        int result=requestime.compareTo(systime);
        if (result!=0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public Object other(int carid, String requestime, String mac) throws Exception {
        int returnresult =0;
        Result resultObj = new Result();
        Auth_token tokenObj = new Auth_token();
        Auth_key keyObj = new Auth_key();
        Respcode code = new Respcode();
        String tvaildate = String.valueOf(new SimpleDateFormat("yyyyMsMddHHmmsSSS").parse(requestime).getTime()+3600000);
        String kvaildate = String.valueOf(new SimpleDateFormat("yyyyMsMddHHmmsSSS").parse(requestime).getTime()+3600000);
        try {
            //auth处理完成转发给v2x
            Auth_token auth_tokenfin = tokenMapper.selectByPrimaryKey(carid);
            Auth_key auth_keyfin = keyMapper.selectByPrimaryKey(carid);

            // key validtime  timestamp id token validtime timestamp
            String id =String.valueOf(auth_tokenfin.getCarid());
            String token1 =auth_tokenfin.getAuthtoken();
            String tokenstatus =auth_tokenfin.getStatus();
            String keystatus =auth_keyfin.getStatus();
            String key = auth_keyfin.getAuthkey();
            String tokenvalidtime =new SimpleDateFormat("yyyyMsMddHHmmsSSS").format(auth_tokenfin.getTimestamp());
            String keyvalidtime =new SimpleDateFormat("yyyyMsMddHHmmsSSS").format(auth_keyfin.getTimestamp());


            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<String, String>();


            params.put("id",id);
            params.put("token",token1);
            params.put("key",key);
            params.put("timestamp",requestime);
            params.put("validtime",tvaildate);
//            params.put("validtime",kvaildate);

            //auth db 操作 新增  之后 请求http放到事务外
            String url  ="http://localhost:8080/test";
            ResponseEntity<String> response = restTemplate.postForEntity( url, params , String.class );
            System.out.println(response.getBody());
            String mssage = response.getBody();//返回结果和具体信息（token、key）
            JSONObject obj1 = JSONObject.fromObject(mssage);
            if (mssage.length() == 0) {
                code.setCode(10505);
                code.setMessage(Respcode.NOBACK_MES);
                return code;
            }

            returnresult = obj1.getInt("result");
            String returntimestamp = obj1.getString("timestamp");

            if(returnresult==0){
                //成功
                //v2x收到信息返回成功auth 更新token表,key表状态为处理完成
                if(tokenstatus.equals("初始") && keystatus.equals("初始")) {
                    tokenObj.setStatus("处理完成");
                    tokenObj.setCarid(carid);
                    tokenObj.setVersion(0);
                    //int i =tokenService.updateToken(token);
                    int i = tokenMapper.updateByKeyAndStatus(tokenObj);

                    keyObj.setStatus("处理完成");
                    keyObj.setCarid(carid);
                    keyObj.setVersion(0);
                    int i1 = keyMapper.updateByKeyAndStatus(keyObj);
                    if (i < 1 || i1 < 1) {
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }
                }
                //拿到数据
                //调用AES工具类把拿到的数据进行加密
                String password =  EncryptionUtil.encrypt(token1);
                resultObj.setToken(password);
                resultObj.setKey(EncryptionUtil.encrypt(key));
                resultObj.setTokenvalidtime(tokenvalidtime);
                resultObj.setKeyvalidtime(keyvalidtime);
                resultObj.setResult(returnresult);
                resultObj.setRandomnum((int)(Math.random()*100+1));//1到100随机数
                return  resultObj;
            }else if(returnresult==1){//终端不合法
                throw new Exception("终端不合法");
            }else if(returnresult==2){//报文不能识别
                throw new Exception("报文不能识别");
            }else{
                throw new Exception("转发系统内部错误");
            }
        }catch (Exception e){
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if(returnresult==1){//不合法
                code.setCode(10503);
                code.setMessage(Respcode.NO_MES);
                return code;
            }else if(returnresult==2) {//不识别
                code.setCode(10504);
                code.setMessage(Respcode.NOTKNOW_MES);
                return code;
            }else if(returnresult==99){
                code.setCode(10507);
                code.setMessage(Respcode.REQ_MES);
                return code;
            }
        }
        return code;
    }

    private Respcode tokenandkeyisnull(int carid, String requestime, String mac) throws Exception{
        Respcode code = new Respcode();
        Result resultObj = new Result();
        Auth_token auth_token = tokenMapper.selectByPrimaryKey(carid);
        Auth_key auth_key = keyMapper.selectByPrimaryKey(carid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
//        String tvaildate = String.valueOf(sdf.parse(requestime).getTime()+3600000);
//        String kvaildate = String.valueOf(sdf.parse(requestime).getTime()+3600000);
        Auth_token tokenObj = new Auth_token();
        Auth_key keyObj = new Auth_key();
        int returnresult=0;
        try {
            tokenObj.setCarid(carid);
            tokenObj.setMac(mac);
            tokenObj.setStatus("初始");
            tokenObj.setVersion(0);
            tokenObj.setTimestamp(sdf.parse(requestime));
            tokenObj.setCreateTime(new Date(sdf.parse(requestime).getTime()+3600000));
            tokenObj.setAuthtoken(UUID.randomUUID().toString());
            int result = tokenMapper.insert(tokenObj);

            keyObj.setCarid(carid);
            keyObj.setMac(mac);
            keyObj.setStatus("初始");
            keyObj.setVersion(0);
            keyObj.setTimestamp(sdf.parse(requestime));
            keyObj.setCreateTime(new Date(sdf.parse(requestime).getTime()+3600000));
            keyObj.setAuthkey(UUID.randomUUID().toString());
            int result1 = keyMapper.insert(keyObj);
            if(result1<1 ||result<1 ) {
                code.setCode(10501);
                code.setMessage(Respcode.ERROR_MES);
                return code;
            }

            /*//auth处理完成转发给v2x
            Auth_token auth_tokenfin = tokenMapper.selectByPrimaryKey(carid);
            Auth_key auth_keyfin = keyMapper.selectByPrimaryKey(carid);

            // key validtime  timestamp id token validtime timestamp
            String id =String.valueOf(auth_tokenfin.getCarid());
            String token =auth_tokenfin.getAuthtoken();
            String key = auth_keyfin.getAuthkey();
            String tokenvalidtime =sdf.format(auth_tokenfin.getTimestamp());
            String keyvalidtime =sdf.format(auth_keyfin.getTimestamp());


            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<String, String>();


            params.put("id",id);
            params.put("token",token);
            params.put("key",key);
            params.put("timestamp",requestime);
            params.put("validtime",tvaildate);
            params.put("validtime",kvaildate);

            //auth db 操作 新增  之后 请求http放到事务外
            String url  ="http://localhost:8080/test";
            ResponseEntity<String> response = restTemplate.postForEntity( url, params , String.class );
            System.out.println(response.getBody());
            String mssage = response.getBody();//返回结果和具体信息（token、key）
            JSONObject obj = JSONObject.fromObject(mssage);
            if (mssage.length() == 0) {
                code.setCode(10505);
                code.setMessage(Respcode.NOBACK_MES);
                return code;
            }

            returnresult = obj.getInt("result");
            String returntimestamp = obj.getString("timestamp");

            if(returnresult==0){
                //成功
                //v2x收到信息返回成功auth 更新token表,key表状态为处理完成
                tokenObj.setStatus("处理完成");
                tokenObj.setCarid(carid);
                tokenObj.setVersion(0);
                //int i =tokenService.updateToken(token);
                int i = tokenMapper.updateByKeyAndStatus(tokenObj);

                keyObj.setStatus("处理完成");
                keyObj.setCarid(carid);
                keyObj.setVersion(0);
                int i1 = keyMapper.updateByKeyAndStatus(keyObj);
                if(i<1 || i1<1) {
                    code.setCode(10501);
                    code.setMessage(Respcode.ERROR_MES);
                    return code;
                }

                //拿到数据
                //调用AES工具类把拿到的数据进行加密
                String password =  EncryptionUtil.encrypt(token);
                resultObj.setToken(password);
                resultObj.setKey(EncryptionUtil.encrypt(key));
                resultObj.setTokenvalidtime(tokenvalidtime);
                resultObj.setKeyvalidtime(keyvalidtime);
                resultObj.setResult(returnresult);
                resultObj.setRandomnum((int)(Math.random()*100+1));//1到100随机数
                return  resultObj;
            }else if(returnresult==1){//终端不合法
                throw new Exception("终端不合法");
            }else if(returnresult==2){//报文不能识别
                throw new Exception("报文不能识别");
            }else{
                throw new Exception("转发系统内部错误");
            }*/
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if(returnresult==1){//不合法
                code.setCode(10503);
                code.setMessage(Respcode.NO_MES);
                return code;
            }else if(returnresult==2) {//不识别
                code.setCode(10504);
                code.setMessage(Respcode.NOTKNOW_MES);
                return code;
            }else if(returnresult==99){
                code.setCode(10506);
                code.setMessage(Respcode.V2X_MES);
                return code;
            }
        }
        return code;
    }


    @Override
    public Auth_token getTokenByID(int id) {
        Auth_token auth_token = tokenMapper.selectByPrimaryKey(id);
        return auth_token;
    }

    @Override
    public int updateToken(Auth_token token) {
        return tokenMapper.updateByPrimaryKeySelective(token);
    }

    @Override
    public int updateTokenAndStatus(Auth_token token) {
        return tokenMapper.updateByKeyAndStatus(token);
    }

    @Override
    public int deleteToken(int id) {
        return 0;
    }

    @Override
    public int insertToken(Auth_token token) {
        return 0;
    }

    @Override
    public int addBatch(Auth_token token) {
        return 0;
    }

    @Override
    public List<Integer> tokenIds() {
        return null;
    }

    @Override
    public int batchInsert(List<Auth_token> list) {
        return 0;
    }

    @Override
    public List<Auth_token> batchSelect(List<Long> list) {
        return null;
    }

    @Override
    public int batchUpdate(List<Auth_token> list) {
        return 0;
    }

    @Override
    public int batchDelete(List<Long> userInfoId) {
        return 0;
    }


    public long checktime(String dbDate) throws  Exception {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        Date now = new Date();
        Date d3 = new Date(now.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date d1 = df.parse(dbDate);

        Date afterDate = new Date(new Date().getTime() + 3600000);
        Date sysDate = new Date(new Date().getTime());
        Date beforeDate = new Date(new Date().getTime() + 600000);
//        return 10;

//        请求时间 +超时时间-当前时间 -提前提起时间（15）
//        long diff = d1.getTime() + afterDate.getTime() - sysDate.getTime() - beforeDate.getTime();
        long diff = d1.getTime()+3600000-sysDate.getTime()-600000;
        long minute = diff / nm;

        if (diff < 0) {//token 已过期   删除 后新增 到log 新增原表 发请求  更新状态
        return -1;
        } else {
            System.out.println("相差的毫秒数为：" + minute);
            return minute;
        }
    }
}