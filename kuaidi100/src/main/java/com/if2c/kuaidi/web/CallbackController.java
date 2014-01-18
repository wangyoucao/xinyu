package com.if2c.kuaidi.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.if2c.kuaidi.entity.Kuaidi;
import com.if2c.kuaidi.pojo.NoticeRequest;
import com.if2c.kuaidi.pojo.NoticeResponse;
import com.if2c.kuaidi.pojo.Result;
import com.if2c.kuaidi.pojo.TaskRequest;
import com.if2c.kuaidi.pojo.TaskResponse;
import com.if2c.kuaidi.service.KuaidiCallBackService;
import com.if2c.kuaidi.web.util.JacksonHelper;

@Controller
public class CallbackController {

    private static final Log log = LogFactory.getLog(CallbackController.class);
    @Autowired
    private KuaidiCallBackService kuaidiCallBackService;

    @RequestMapping(value = "")
    public String listView() {
        return "";
    }
    @ResponseBody
    @RequestMapping(value = "/callback", method = RequestMethod.POST,produces = "application/json")
    public NoticeResponse receiveCallBack(@RequestParam("param") String body, @RequestParam("sign") String signValue) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        NoticeResponse resp = new NoticeResponse();
       
        System.out.println(body);
        try {
            // String param = request.getParameter("param");
            NoticeRequest nReq = com.alibaba.fastjson.JSON.parseObject(body, NoticeRequest.class);
            Result result = nReq.getLastResult();
            String companyName = result.getCom();
            String mailNo = result.getNu();
            // 快递单监控状态
            String status = nReq.getStatus();
            String kuaidiInfo = body;
            Kuaidi kuaidi = new Kuaidi();
            kuaidi.setCompanyName(companyName);
            kuaidi.setMailNo(mailNo);
            kuaidi.setStatus(status);
            kuaidi.setKuaidiInfo(kuaidiInfo);
            kuaidi.setCreate_time(new Date());
            kuaidi.setUpdate_time(new Date());
            kuaidiCallBackService.insertMailNoInfo(kuaidi);
            // 处理快递结果
            resp.setResult(true);
            resp.setReturnCode("200");
            resp.setMessage("成功");
            //response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResult(false);
            resp.setReturnCode("500");
            resp.setMessage("保存失败");
           
           // response.getWriter().print(JacksonHelper.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
        }

        return resp;
    }

    @ResponseBody
    @RequestMapping(value = "/getMailNoStatusInfo", method = RequestMethod.GET, produces = "application/json")
    public String getMailNoInfo(@RequestParam("name") String name, @RequestParam("mailNo") String mailNo) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        return kuaidiCallBackService.find(name, mailNo);

    }

    @SuppressWarnings("unused")
    @ResponseBody
    @RequestMapping(value = "/postMailNoInfo", method = RequestMethod.GET, produces = "application/json")
    public String postMailNoInfo(@RequestParam("companyName") String name, @RequestParam("mailNo") String mailNo) throws ParseException {
        TaskRequest req = new TaskRequest();
        req.setCompany(name);
        // req.setFrom("上海浦东新区");
        // req.setTo("广东深圳南山区");
        req.setNumber(mailNo);
        req.getParameters().put("callbackurl", "http://p.if2c.com/kuaidi100");
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
                
                
                if (resp.getResult() == true) {
                   log.debug("订阅成功");
                    return "sucess";
                } else {
                    log.debug("订阅失败");
                    log.debug("returnCode=:" + resp.getReturnCode());
                    log.debug("returnMessage:"+resp.getMessage());
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
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return responseContent;
    }
}
