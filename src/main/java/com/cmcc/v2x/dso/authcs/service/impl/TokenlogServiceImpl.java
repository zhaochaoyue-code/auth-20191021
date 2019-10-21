package com.cmcc.v2x.dso.authcs.service.impl;

import com.cmcc.v2x.dso.authcs.mapper.Auth_tokenMapper;
import com.cmcc.v2x.dso.authcs.mapper.Auth_tokenlogMapper;
import com.cmcc.v2x.dso.authcs.model.Auth_tokenlog;
import com.cmcc.v2x.dso.authcs.service.TokenlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TokenlogServiceImpl implements TokenlogService {
    @Autowired
    private Auth_tokenlogMapper tokenlogMapper;

    @Override
    public Auth_tokenlog getKeyByID(int id) {
        Auth_tokenlog auth_tokenlog = tokenlogMapper.selectByPrimaryKey(id);
        return auth_tokenlog;
    }

    @Override
    public int updateKey(Auth_tokenlog key) {
        return 0;
    }

    @Override
    public int deleteKey(int id) {
        return 0;
    }

    @Override
    public int insertKey(Auth_tokenlog key) {
        return 0;
    }

    @Override
    public int updateKeyAndStatus(Auth_tokenlog key) {
        return 0;
    }

    @Override
    public int batchInsert(List<Auth_tokenlog> list) {
        return 0;
    }

    @Override
    public List<Auth_tokenlog> batchSelect(List<Long> list) {
        return null;
    }

    @Override
    public int batchUpdate(List<Auth_tokenlog> list) {
        return 0;
    }

    @Override
    public int batchDelete(List<Long> userInfoId) {
        return 0;
    }
}
