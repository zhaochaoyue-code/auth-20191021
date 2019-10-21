package com.cmcc.v2x.dso.authcs.service;

import com.cmcc.v2x.dso.authcs.model.Auth_tokenlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TokenlogService {

    public Auth_tokenlog getKeyByID(int id);
    public int updateKey(Auth_tokenlog key);
    public  int deleteKey(int id);
    public int insertKey(Auth_tokenlog key);
    public int updateKeyAndStatus(Auth_tokenlog key);

    int batchInsert(@Param("list") List<Auth_tokenlog> list);//批量插入
    List<Auth_tokenlog> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Auth_tokenlog> list);//批量更新
    int batchDelete(List<Long> userInfoId);
}
