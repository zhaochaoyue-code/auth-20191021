package com.cmcc.v2x.dso.authcs.service.impl;

import com.cmcc.v2x.dso.authcs.mapper.Auth_keylogMapper;
import com.cmcc.v2x.dso.authcs.model.Auth_keylog;
import com.cmcc.v2x.dso.authcs.service.KeylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KeylogServiceImpl implements KeylogService {
    @Autowired
    private Auth_keylogMapper keylogMapper;


    @Override
    public Auth_keylog getKeyByID(int id) {
        return null;
    }

    @Override
    public int updateKey(Auth_keylog key) {
        return 0;
    }

    @Override
    public int deleteKey(int id) {
        return 0;
    }

    @Override
    public int insertKey(Auth_keylog key) {
        return 0;
    }

    @Override
    public int updateKeyAndStatus(Auth_keylog key) {
        return 0;
    }

    @Override
    public int batchInsert(List<Auth_keylog> list) {
        return 0;
    }

    @Override
    public List<Auth_keylog> batchSelect(List<Long> list) {
        return null;
    }

    @Override
    public int batchUpdate(List<Auth_keylog> list) {
        return 0;
    }

    @Override
    public int batchDelete(List<Long> userInfoId) {
        return 0;
    }
}
