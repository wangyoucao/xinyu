/**
 * 
 */
package com.if2c.kuaidi.web.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.if2c.kuaidi.pojo.TaskRequest;
import com.if2c.kuaidi.pojo.TaskResponse;
import com.if2c.kuaidi.service.KuaidiCallBackService;

/**
 * @author 131528
 */
public class PostExpressNum {
    private static final Log log = LogFactory.getLog(PostExpressNum.class);
    @Autowired
    private KuaidiCallBackService kuaidiCallBackService;

    public void work() {
        List<Map<String, Object>> lst = kuaidiCallBackService.searchAllNoPostExpressNum();
        try {
            for (Map<String, Object> map : lst) {
                String companyCode = map.get("express_code").toString();
                String express_num = map.get("express_num").toString();

                String response = postMailNoInfo(companyCode, express_num);
                // 成功发送，更新数据状态
                if (response.equals("success")) {
                    try {
                        kuaidiCallBackService.updateExpressNumStatus(companyCode, express_num);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("lst:" + lst.toString());
    }

    public String postMailNoInfo(String companyCode, String exoress_num) throws ParseException {
        TaskRequest req = new TaskRequest();
        req.setCompany(companyCode);
       // req.setFrom("上海浦东新区");
      //  req.setTo("广东深圳南山区");
        req.setNumber(exoress_num);
        req.getParameters().put("callbackurl", "http://www.yourdmain.com/kuaidi");
        req.setKey("");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // httpclient.getParams().setParameter(HttpParams.HTTP_CONTENT_CHARSET, "UTF-8");
        String responseContent = null;

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("schema", "json"));
        params.add(new BasicNameValuePair("param", JacksonHelper.toJSON(req)));
        try {
            HttpPost httppost = new HttpPost("http://www.kuaidi100.com/poll");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // Execute HTTP request
            log.debug("executing request " + httppost.getURI());
            try {
                response = httpclient.execute(httppost);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            log.debug("----------------------------------------");
            log.debug(response.getStatusLine());
            log.debug("----------------------------------------");

            HttpEntity entity = response.getEntity();
            log.debug("ssssss" + entity.getContentEncoding());
            log.debug(entity.getContentType());

            if (entity != null) {
                try {
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TaskResponse resp = JacksonHelper.fromJSON(responseContent, TaskResponse.class);
                System.out.println("returnCode=:" + resp.getReturnCode());
                System.out.println("returnMessage=:" + resp.getMessage());
                
                if (resp.getResult() == true) {
                    log.debug("订阅成功");
                    return "success";
                } else {
                    
                    log.debug("订阅失败");
                    log.debug("returnCode=:" + resp.getReturnCode());
                    log.debug("returnMessage=:" + resp.getMessage());
                    return "failure";
                }
            }
        } catch (Exception e) {
            log.debug("协议异常:" + e.getMessage());
            e.printStackTrace();
            return "failure";
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

}
