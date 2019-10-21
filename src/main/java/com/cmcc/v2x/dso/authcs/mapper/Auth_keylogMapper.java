package com.cmcc.v2x.dso.authcs.mapper;

import com.cmcc.v2x.dso.authcs.model.Auth_keylog;

public interface Auth_keylogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth_keylog record);

    int insertSelective(Auth_keylog record);

    Auth_keylog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auth_keylog record);

    int updateByPrimaryKey(Auth_keylog record);


}