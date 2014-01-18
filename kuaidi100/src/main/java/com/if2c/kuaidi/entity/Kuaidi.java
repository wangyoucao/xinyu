/**
 * 
 */
package com.if2c.kuaidi.entity;

import java.util.Date;

/**
 * @author 131528
 *
 */
public class Kuaidi {
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getMailNo() {
        return mailNo;
    }
    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getKuaidiInfo() {
        return kuaidiInfo;
    }
    public void setKuaidiInfo(String kuaidiInfo) {
        this.kuaidiInfo = kuaidiInfo;
    }
   
    public Date getCreate_time() {
        return create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public Date getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    String companyName;
    String mailNo;
    String status;
    String kuaidiInfo;
    Date  create_time;
    Date  update_time;

}
