package com.cmcc.v2x.dso.authcs.mapper;

import com.cmcc.v2x.dso.authcs.model.Auth_token;

import java.util.List;

public interface Auth_tokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth_token record);

    int insertSelective(Auth_token record);

    Auth_token selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth_token record);

    int updateByPrimaryKey(Auth_token record);

    int updateByKeyAndStatus(Auth_token record);

//    int batchinsert(List<Auth_token> tokenList);//批量插入
//
//    List<Auth_token> batchSelect(List<Long> list);//批量查询
//
//    int batchUpdate(List<Auth_token> list);//批量更新
//
//    int batchDelete(List<Long> userInfoId);//批量删除
}