package com.cmcc.v2x.dso.authcs.service;

import com.cmcc.v2x.dso.authcs.model.Obu_data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ObudataService {

    public Obu_data getKeyByID(int id);
    public int updateKey(Obu_data key);
    public  int deleteKey(int id);
    public int insertKey(Obu_data key);
    public int updateKeyAndStatus(Obu_data key);

    int batchInsert(@Param("list") List<Obu_data> list);//批量插入
    List<Obu_data> batchSelect(List<Long> list);//按ID批量查询//按ID查询
    int batchUpdate(List<Obu_data> list);//批量更新
    int batchDelete(List<Long> userInfoId);
}
