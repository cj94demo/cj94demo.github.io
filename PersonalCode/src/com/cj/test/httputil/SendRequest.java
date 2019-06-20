package com.cj.test.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSONObject;

/**
 * 
 * 发送请求 httpclient封装
 * @author bjedc
 * @version v1.0
 */
@SuppressWarnings("deprecation")
public class SendRequest
{

    private static DefaultHttpClient client = new DefaultHttpClient();

    /**
     * 发送Get请求
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result sendGet(String url, Map<String, Object> headers, Map<String, Object> params, String encoding,
            boolean duan) throws ClientProtocolException, IOException {
        url = url + (null == params ? "" : assemblyParameter(params));
        HttpGet hp = new HttpGet(url);
        if (null != headers)
            hp.setHeaders(assemblyHeader(headers));
        HttpResponse response = client.execute(hp);
        if (duan)
            hp.abort();
        HttpEntity entity = response.getEntity();
        Result result = new Result();
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setHeaders(response.getAllHeaders());
        result.setHttpEntity(entity);
        return result;
    }

    public static Result sendGet(String url, Map<String, Object> headers, Map<String, Object> params, String encoding)
            throws ClientProtocolException, IOException {
        return sendGet(url, headers, params, encoding, false);
    }
    
    public static String post(String url, JSONObject json) {
        
        CloseableHttpClient client = null;
        HttpPost post = new HttpPost(url);
        
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        
        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            client = HttpClients.createDefault();
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                
                    System.out.println("请求服务器成功，做相应处理");
                
            } else {
                
                System.out.println("请求服务端失败");
                
            }
            

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 发送Post请求
     * 
     * @param url
     *            请求的地址
     * @param headers
     *            请求的头部信息
     * @param params
     *            请求的参数
     * @param encoding
     *            字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result sendPost(String url, Map<String, Object> headers, Map<String, Object> params, String encoding)
            throws ClientProtocolException, IOException {
        HttpPost post = new HttpPost(url);

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : params.keySet()) {
            if ("properties".equals(temp)) {
                String value =  params.get(temp).toString();
                list.add(new BasicNameValuePair(temp, value));
            }
            else {
                list.add(new BasicNameValuePair(temp, (String) params.get(temp)));
            }
        }
        post.setEntity(new UrlEncodedFormEntity(list, encoding));

        if (null != headers)
            post.setHeaders(assemblyHeader(headers));
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();

        Result result = new Result();
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setHeaders(response.getAllHeaders());
        result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
        result.setHttpEntity(entity);
        return result;
    }

    /**
     * 组装头部信息
     * 
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, Object> headers) {
        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, (String) headers.get(str));
            i++;
        }
        return allHeader;
    }

    /**
     * 组装Cookie
     * 
     * @param cookies
     * @return
     */
    public static String assemblyCookie(List<Cookie> cookies) {
        StringBuffer sbu = new StringBuffer();
        for (Cookie cookie : cookies) {
            sbu.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        if (sbu.length() > 0)
            sbu.deleteCharAt(sbu.length() - 1);
        return sbu.toString();
    }

    /**
     * 组装请求参数
     * 
     * @param parameters
     * @return
     */
    public static String assemblyParameter(Map<String, Object> parameters) {
        String para = "?";
        for (String str : parameters.keySet()) {
            para += str + "=" + parameters.get(str) + "&";
        }
        return para.substring(0, para.length() - 1);
    }

    // TODO demo
    public static void main(String[] args) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            Result result = SendRequest.sendGet("http://www.baidu.com", param, param, "utf-8");
            // SendRequest.u
            String str = result.getHtml(result, "utf-8");
            System.out.println(str);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
