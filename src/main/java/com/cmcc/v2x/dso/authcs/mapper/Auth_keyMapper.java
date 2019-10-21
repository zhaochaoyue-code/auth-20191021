package com.cmcc.v2x.dso.authcs.mapper;

import com.cmcc.v2x.dso.authcs.model.Auth_key;

import java.util.List;

public interface Auth_keyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth_key record);

    int insertSelective(Auth_key record);

    Auth_key selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth_key record);

    int updateByPrimaryKey(Auth_key record);

    int updateByKeyAndStatus(Auth_key record);

    int batchinsert(List<Auth_key> tokenList);//批量插入

    List<Auth_key> batchSelect(List<Long> list);//批量查询

    int batchUpdate(List<Auth_key> list);//批量更新

    int batchDelete(List<Long> userInfoId);//批量删除
}