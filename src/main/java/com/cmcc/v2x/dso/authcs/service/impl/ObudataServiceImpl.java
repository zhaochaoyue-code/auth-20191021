package com.cmcc.v2x.dso.authcs.service.impl;

import com.cmcc.v2x.dso.authcs.mapper.Obu_dataMapper;
import com.cmcc.v2x.dso.authcs.model.Obu_data;
import com.cmcc.v2x.dso.authcs.service.ObudataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ObudataServiceImpl implements ObudataService {

    @Autowired
    private Obu_dataMapper obuDataMapper;

    @Override
    public Obu_data getKeyByID(int id) {
        Obu_data obu_data = obuDataMapper.selectByPrimaryKey(id);
        return obu_data;
    }

    @Override
    public int updateKey(Obu_data key) {
        return 0;
    }

    @Override
    public int deleteKey(int id) {
        return 0;
    }

    @Override
    public int insertKey(Obu_data key) {
        return 0;
    }

    @Override
    public int updateKeyAndStatus(Obu_data key) {
        return 0;
    }

    @Override
    public int batchInsert(List<Obu_data> list) {
        return 0;
    }

    @Override
    public List<Obu_data> batchSelect(List<Long> list) {
        return null;
    }

    @Override
    public int batchUpdate(List<Obu_data> list) {
        return 0;
    }

    @Override
    public int batchDelete(List<Long> userInfoId) {
        return 0;
    }
}
