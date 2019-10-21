package com.cmcc.v2x.dso.authcs.service.impl;

import com.cmcc.v2x.dso.authcs.mapper.Auth_keyMapper;
import com.cmcc.v2x.dso.authcs.mapper.Auth_keylogMapper;
import com.cmcc.v2x.dso.authcs.model.*;
import com.cmcc.v2x.dso.authcs.model.Error;
import com.cmcc.v2x.dso.authcs.service.KeyService;
import com.cmcc.v2x.dso.authcs.util.EncryptionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class KeyServiceImpl implements KeyService {
    @Autowired
    private Auth_keyMapper keyMapper;

    @Autowired
    private Auth_keylogMapper keylogMapper;

    @Override
    public Auth_key getKeyByID(int id) {
        return keyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateKey(Auth_key key) {
        return 0;
    }

    @Override
    public int deleteKey(int id) {
        return 0;
    }

    @Override
    public int insertKey(Auth_key key) {
        return 0;
    }

    @Override
    public int updateKeyAndStatus(Auth_key key) {
        return keyMapper.updateBcyKeyAndStatus(key);
    }

    @Override
    public int batchInsert(List<Auth_key> list) {
        return 0;
    }

    @Override
    public List<Auth_key> batchSelect(List<Long> list) {
        return null;
    }

    @Override
    public int batchUpdate(List<Auth_key> list) {
        return 0;
    }

    @Override
    public int batchDelete(List<Long> userInfoId) {
        return 0;
    }

    @Transactional
    @Override
    public Object getKeyObj(int carid, String requestime, String mac) throws Exception {
        Respcode code = new Respcode();
        Result resultObj = new Result();
        Auth_key auth_key = keyMapper.selectByPrimaryKey(carid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMsMddHHmmsSSS");
        Auth_key keyObj = new Auth_key();
        int returnresult =0;
        try {
            if (auth_key == null) {
                keyObj.setCarid(carid);
                keyObj.setMac(mac);
                keyObj.setStatus("初始");
                keyObj.setVersion(0);
                keyObj.setTimestamp(sdf.parse(requestime));
                keyObj.setAuthkey(UUID.randomUUID().toString());
                int result = keyMapper.insert(keyObj);
                if (result < 1) {
                    code.setCode(10501);
                    code.setMessage(Respcode.ERROR_MES);
                    return code;
                }
           /*else {
                code.setCode(10200);
                code.setMessage(Respcode.OK_MESS);
                return code;

            }*/

                //auth处理完成转发给v2x
                Auth_key auth_keyfin = keyMapper.selectByPrimaryKey(carid);
                if (auth_keyfin != null) {
                    // id token validtime timestamp
                    String id = String.valueOf(auth_keyfin.getCarid());
                    String key = auth_keyfin.getAuthkey();
//                String validtime =auth_tokenfin.get
                    String timestamp = sdf.format(auth_keyfin.getTimestamp());
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("key", key);
                    params.put("validtime", timestamp);
                    params.put("timestamp", timestamp);
                    String url = "http://localhost:8080/test";
                    ResponseEntity<String> response = restTemplate.postForEntity(url, params, String.class);
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

                    if (returnresult == 0) {//成功

                        //v2x收到信息返回成功auth 更新token表状态为处理完成
                        keyObj.setStatus("处理完成");
                        keyObj.setCarid(carid);
                        keyObj.setVersion(0);
                        //          int i =tokenService.updateToken(token);
                        int i = keyMapper.updateByKeyAndStatus(keyObj);
                        if (i < 1) {
                            code.setCode(10501);
                            code.setMessage(Respcode.ERROR_MES);
                            return code;
                        }
                        //拿到数据
                        //调用AES工具类把拿到的数据进行加密
                        String password = EncryptionUtil.encrypt(key);
                        resultObj.setKey(password);
                        resultObj.setKeyvalidtime(returntimestamp);
                        resultObj.setResult(returnresult);
                        resultObj.setTokenvalidtime(returntimestamp);
                        return resultObj;
                    } else if (returnresult == 1) {//终端不合法
                        throw new Exception(String.valueOf(Error.NO_TOKEN_FOUND));
                    } else {//报文不能识别
                        throw new Exception(String.valueOf(Error.NO_TOKEN_FOUND));
                    }

                }
            } else {
                String status = auth_key.getStatus();
                Date date = auth_key.getTimestamp();
                String authkey = auth_key.getAuthkey();
                int car = auth_key.getCarid();
                String ma = auth_key.getMac();
                int ver = auth_key.getVersion();
                String reqtime = sdf.format(auth_key.getTimestamp());
                long checktime = checktime(reqtime);
                if (checktime < 0 && (auth_key.getStatus()).equals("处理完成")) {

                    int a = keyMapper.deleteByPrimaryKey(carid);
                    if (a < 1) {
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }
                    Auth_keylog authKeylog = new Auth_keylog();
                    authKeylog.setAuthkey(authkey);
                    authKeylog.setCarid(car);
                    authKeylog.setTimestamp(date);
                    authKeylog.setMac(ma);
                    authKeylog.setVersion(ver);
                    authKeylog.setStatus(status);
                    int b = keylogMapper.insertSelective(authKeylog);
                    if (b < 1) {
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    }

                    Auth_key akey = new Auth_key();
                    akey.setCarid(carid);
                    akey.setMac(mac);
                    akey.setStatus("初始");
                    akey.setVersion(0);
                    akey.setTimestamp(sdf.parse(requestime));
                    akey.setAuthkey(UUID.randomUUID().toString());

                    int c = keyMapper.insertSelective(akey);
                    if (c < 1) {
                        code.setCode(10501);
                        code.setMessage(Respcode.ERROR_MES);
                        return code;
                    } else {
                        code.setCode(10200);
                        code.setMessage(Respcode.OK_MESS);
                        return code;
                    }
//                int result = tokenService.deleteToken(carid);
//                //新增tokelog
//                int a = tokenService.insertToken(token);
                }

                if (checktime > 0 && (auth_key.getStatus()).equals("处理完成")) {
                    code.setCode(10201);
                    String timeMes = "已请求token " + Respcode.TIME_MES + checktime + "毫秒";
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
        long diff = d1.getTime() + afterDate.getTime() - sysDate.getTime() - beforeDate.getTime();

        long minute = diff / nm;

        return  -1;
        /*if (diff < 0) {//token 已过期
            return -1;
        } else {
            System.out.println("还差相差的分钟数为：" + minute);
            return minute;
        }*/
    }
}
