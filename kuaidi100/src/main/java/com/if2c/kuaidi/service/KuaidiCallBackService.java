package com.if2c.kuaidi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if2c.kuaidi.entity.Kuaidi;
import com.if2c.kuaidi.repository.KuaidiCallBackDao;

@Service
public class KuaidiCallBackService {
    @Autowired
    private KuaidiCallBackDao kuaidiCallBackDao;

    public void insertMailNoInfo(Kuaidi kuaidi) {
        System.out.println("order_express_no:"+kuaidi.getMailNo());
        kuaidiCallBackDao.insertMailNoInfo(kuaidi);
    }

    public String find(String name, String mailNo) {
        return kuaidiCallBackDao.find(name, mailNo);

    }
    
    public List<Map<String,Object>> searchAllNoPostExpressNum() {
        return kuaidiCallBackDao.searchAllNoPostExpressNum();

    }
    
    public void updateExpressNumStatus(String companyCode,String express_num){
        kuaidiCallBackDao.updateExpressNumStatus(companyCode,express_num); 
    }
    
    // @Autowired
    // public void setKuaidiCallBackDao(KuaidiCallBackDao kuaidiCallBackDao) {
    // this.kuaidiCallBackDao = kuaidiCallBackDao;
    // }
}
