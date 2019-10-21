package com.cmcc.v2x.dso.authcs.mapper;

import com.cmcc.v2x.dso.authcs.model.Obu_data;

public interface Obu_dataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Obu_data record);

    int insertSelective(Obu_data record);

    Obu_data selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Obu_data record);

    int updateByPrimaryKey(Obu_data record);
}