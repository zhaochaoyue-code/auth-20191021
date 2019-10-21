package com.cmcc.v2x.dso.authcs.service;

import com.cmcc.v2x.dso.authcs.model.Auth_keylog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeylogService {

    public Auth_keylog getKeyByID(int id);
    public int updateKey(Auth_keylog key);
    public  int deleteKey(int id);
    public int insertKey(Auth_keylog key);
    public int updateKeyAndStatus(Auth_keylog key);

    int batchInsert(@Param("list") List<Auth_keylog> list);//批量插入
    List<Auth_keylog> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Auth_keylog> list);//批量更新
    int batchDelete(List<Long> userInfoId);
}
