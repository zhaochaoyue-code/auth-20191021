package com.cmcc.v2x.dso.authcs.mapper;

import com.cmcc.v2x.dso.authcs.model.Auth_tokenlog;

import java.util.List;

public interface Auth_tokenlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth_tokenlog record);

    int insertSelective(Auth_tokenlog record);

    Auth_tokenlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth_tokenlog record);

    int updateByPrimaryKey(Auth_tokenlog record);

    int updateByKeyAndStatus(Auth_tokenlog record);

//    int batchinsert(List<Auth_tokenlog> tokenList);//批量插入
//    List<Auth_tokenlog> batchSelect(List<Long> list);//批量查询
//    int batchUpdate(List<Auth_tokenlog> list);//批量更新
//    int batchDelete(List<Long> userInfoId);//批量删除
}