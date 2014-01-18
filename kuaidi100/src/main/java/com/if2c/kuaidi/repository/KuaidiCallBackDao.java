package com.if2c.kuaidi.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.if2c.kuaidi.entity.Kuaidi;

@MyBatisRepository
public interface KuaidiCallBackDao {
    public String find(@Param("companyName") String name, @Param("mailNo") String mailNo);
    public void insertMailNoInfo(Kuaidi kuaidi);
    public List<Map<String,Object>> searchAllNoPostExpressNum();
    public void updateExpressNumStatus(@Param("companyCode")String companyCode,@Param("express_num")String express_num);

}
