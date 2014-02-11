/**
 * 
 */
package rwar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author 131528
 *
 */
public class HttpTest  {
    @Test
    public void insert( ) throws IOException {

       
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // httpclient.getParams().setParameter(HttpParams.HTTP_CONTENT_CHARSET, "UTF-8");
        String responseContent = null;

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("uid", "wwww"));
        params.add(new BasicNameValuePair("username", "aaa"));
        // for test
     
       
        boolean bResult = false;
        try {
            String url="http://localhost:8080/rwar/addOrUpdateUser";
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // Execute HTTP request
          
            try {
                response = httpclient.execute(httppost);
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

           
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
                try {
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // JSONObject jsonObj = JSONObject.fromObject(responseContent);
             
             //   bResult = Boolean.parseBoolean(result);
            }
        } catch (Exception e) {
           // log.debug("协议异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != response) {
                response.close();
            }
            httpclient.close();
        }

        
        // return responseContent;

    }


}
