package com.cmcc.v2x.dso.authcs.service;

import com.cmcc.v2x.dso.authcs.model.Auth_token;
import com.cmcc.v2x.dso.authcs.model.Respcode;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;

public interface TokenService {

    Object getObj(int carid,String requestime,String mac) throws Exception;
    Respcode getAllObj(int carid, String requestime, String mac) throws Exception;
    Object other(int carid,String requestime,String mac)throws Exception;

     Auth_token getTokenByID(int id);
     int updateToken(Auth_token token);

     int updateTokenAndStatus(Auth_token token);
     int deleteToken(int id);

     int insertToken(Auth_token token);
     int addBatch(Auth_token token);

     List<Integer> tokenIds();

    int batchInsert(@Param("list") List<Auth_token> list);//批量插入
    List<Auth_token> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Auth_token> list);//批量更新
    int batchDelete(List<Long> userInfoId);
}
