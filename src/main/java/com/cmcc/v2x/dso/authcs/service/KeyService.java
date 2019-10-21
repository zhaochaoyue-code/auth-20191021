package com.cmcc.v2x.dso.authcs.service;

import com.cmcc.v2x.dso.authcs.model.Auth_key;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeyService {

    public Auth_key getKeyByID(int id);
    public int updateKey(Auth_key key);
    public  int deleteKey(int id);
    public int insertKey(Auth_key key);
    public int updateKeyAndStatus(Auth_key key);

    int batchInsert(@Param("list") List<Auth_key> list);//批量插入
    List<Auth_key> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Auth_key> list);//批量更新
    int batchDelete(List<Long> userInfoId);
    Object getKeyObj(int carid,String requestime,String mac) throws Exception;
}
