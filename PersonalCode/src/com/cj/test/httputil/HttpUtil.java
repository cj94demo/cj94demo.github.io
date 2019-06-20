package com.cj.test.httputil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient工具类，使用http-client包实现，原先的common-httpclient已经淘汰
 * 
 * @author ko
 * @version [版本号, 2017年10月18日]
 */
public class HttpUtil
{
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        // 在提交请求之前 测试连接是否可用
        connMgr.setValidateAfterInactivity(1);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET请求
     * 
     * @param apiUrl
     *            API接口URL
     * @return String 响应内容
     */
    public static String doGet(String apiUrl) {
        return doHttp(apiUrl, null, "get", 2, false);
    }

    /**
     * 发送 GET请求(SSL)
     * 
     * @param apiUrl
     *            API接口URL
     * @return String 响应内容
     */
    public static String doGetSSL(String apiUrl) {
        return doHttp(apiUrl, null, "get", 2, true);
    }

    /**
     * 发送POST请求
     * 
     * @param apiUrl
     *            API接口URL
     * @param params
     *            K-V参数
     * @return String 响应内容
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        return doHttp(apiUrl, params, "post", 2, false);
    }

    public static String doPost(String apiUrl, Map<String, Object> params, Map<String, Object> headers) {
        return doHttp(apiUrl, params, headers, "post", 2, false);
    }

    /**
     * 发送POST请求(SSL)
     * 
     * @param apiUrl
     *            API接口URL
     * @param params
     *            K-V参数
     * @return String 响应内容
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        return doHttp(apiUrl, params, "post", 2, true);
    }

    /**
     * 发送POST请求
     * 
     * @param apiUrl
     *            API接口URL
     * @param json
     *            json参数
     * @return String 响应内容
     */
    public static String doPostJson(String apiUrl, String json) {
        return doHttp(apiUrl, json, "post", 2, false);
    }

    /**
     * 发送POST请求(SSL)
     * 
     * @param apiUrl
     *            API接口URL
     * @param json
     *            json参数
     * @return String 响应内容
     */
    public static String doPostJsonSSL(String apiUrl, String json) {
        return doHttp(apiUrl, json, "post", 2, true);
    }

    /**
     * 
     *  @param url
     *  @param json
     *  @param headers
     *  @return
     * @exception/throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String doPostJson(String url, String json, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpPost post = new HttpPost(url);

        post.setHeader("Content-Type", "application/json");
        // 拼接header
        if (headers != null) {
            for (String key : headers.keySet()) {
                post.addHeader(key, headers.get(key));
            }
        }

        String result = "";
        try {
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);

            // 发送请求
            httpResponse = httpClient.execute(post);
            // 得到响应信息
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            // 判断响应信息是否正确
            if (statusCode != HttpStatus.SC_OK) {
                // 终止并抛出异常
                post.abort();
                throw new RuntimeException("HttpClient,error status code : " + statusCode);
            }

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                //result = EntityUtils.toString(entity);//不进行编码设置
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        finally {
            //关闭所有资源连接
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送 http 请求
     * 
     * @param apiUrl
     *            API接口URL
     * @param params
     *            {Map<String, Object> K-V形式、json字符串}
     * @param method
     *            {null、或者post:POST请求、patch:PATCH请求、delete:DELETE请求、get:GET请求}
     * @param type
     *            {1:请求返回stream(此时流需要在外部手动关闭),其余:请求返回字符串}
     * @param ssl
     * @return {InputStream或者String}
     */
    @SuppressWarnings("unchecked")
    public static <T> T doHttp(String apiUrl, Object params, String method, int type, boolean ssl) {
        System.out.println("invoking in ---》" + apiUrl);
        CloseableHttpClient httpClient = null;
        if (ssl) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        }
        else {
            httpClient = HttpClients.createDefault();
        }

        HttpRequestBase httpPost = null;
        if (method != null && method != "") {
            if (ExtranetConstant.PATCH.equalsIgnoreCase(method)) {
                httpPost = new HttpPatch(apiUrl);
            }
            else if (ExtranetConstant.DELETE.equalsIgnoreCase(method)) {
                httpPost = new HttpDelete(apiUrl);
            }
            else if (ExtranetConstant.GET.equalsIgnoreCase(method)) {
                httpPost = new HttpGet(apiUrl);
            }
            else if (ExtranetConstant.POST.equalsIgnoreCase(method)) {
                httpPost = new HttpPost(apiUrl);
            }
        }
        else {
            httpPost = new HttpPost(apiUrl);
        }
        CloseableHttpResponse response = null;

        try {
            if (ssl) {
                httpPost.setConfig(requestConfig);
            }
            // 参数不为null、要处理参数
            if (params != null) {
                // get请求拼接在url后面
                if (httpPost instanceof HttpGet) {
                    StringBuffer param = new StringBuffer();
                    if (params instanceof Map) {
                        Map<String, Object> paramsConvert = (Map<String, Object>) params;
                        int i = 0;
                        for (String key : paramsConvert.keySet()) {
                            if (i == 0) {
                                param.append("?");
                            }
                            else {
                                param.append("&");
                            }
                            param.append(key).append("=").append(paramsConvert.get(key));
                            i++;
                        }
                    }
                    else {
                        param.append("?" + params.toString());
                    }
                    apiUrl += param;
                }
                // delete请求暂不处理
                else if (!(httpPost instanceof HttpDelete)) {
                    // K-V形式
                    if (params instanceof Map) {
                        Map<String, Object> paramsConvert = (Map<String, Object>) params;

                        List<NameValuePair> pairList = new ArrayList<>(paramsConvert.size());
                        for (Map.Entry<String, Object> entry : paramsConvert.entrySet()) {
                            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                            pairList.add(pair);
                        }
                        ((HttpEntityEnclosingRequestBase) httpPost)
                                .setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
                    }
                    // json格式
                    else {
                        StringEntity stringEntity = new StringEntity(params.toString(), "UTF-8");
                        stringEntity.setContentEncoding("UTF-8");
                        stringEntity.setContentType("application/json");
                        ((HttpEntityEnclosingRequestBase) httpPost).setEntity(stringEntity);
                    }
                }
            }
            System.out.println("starting doPost ---》");
            response = httpClient.execute(httpPost);
            System.out.println("ending doPost ---》" + response.getAllHeaders());
            // int statusCode = response.getStatusLine().getStatusCode();
            // if (statusCode != HttpStatus.SC_OK) {
            // return null;
            // }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (type == 1) {
                    System.out.println("return value -->" + entity.getContent());
                    return (T) entity.getContent();
                }
                else {
                    // System.out.println("return value -->" +
                    // EntityUtils.toString(entity, "UTF-8"));
                    return (T) EntityUtils.toString(entity, "UTF-8");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("return value -->" + null);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T doHttp(String apiUrl, Object params, Map<String, Object> headers, String method, int type,
            boolean ssl) {
        System.out.println("invoking in ---》" + apiUrl);
        CloseableHttpClient httpClient = null;
        if (ssl) {
            httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        }
        else {
            httpClient = HttpClients.createDefault();
        }

        HttpRequestBase httpPost = null;
        if (method != null && method != "") {
            if (ExtranetConstant.PATCH.equalsIgnoreCase(method)) {
                httpPost = new HttpPatch(apiUrl);
            }
            else if (ExtranetConstant.DELETE.equalsIgnoreCase(method)) {
                httpPost = new HttpDelete(apiUrl);
            }
            else if (ExtranetConstant.GET.equalsIgnoreCase(method)) {
                httpPost = new HttpGet(apiUrl);
            }
            else if (ExtranetConstant.POST.equalsIgnoreCase(method)) {
                httpPost = new HttpPost(apiUrl);
            }
        }
        else {
            httpPost = new HttpPost(apiUrl);
        }
        CloseableHttpResponse response = null;

        try {
            if (ssl) {
                httpPost.setConfig(requestConfig);
            }
            // 参数不为null、要处理参数
            if (params != null) {
                // get请求拼接在url后面
                if (httpPost instanceof HttpGet) {
                    StringBuffer param = new StringBuffer();
                    if (params instanceof Map) {
                        Map<String, Object> paramsConvert = (Map<String, Object>) params;
                        int i = 0;
                        for (String key : paramsConvert.keySet()) {
                            if (i == 0) {
                                param.append("?");
                            }
                            else {
                                param.append("&");
                            }
                            param.append(key).append("=").append(paramsConvert.get(key));
                            i++;
                        }
                    }
                    else {
                        param.append("?" + params.toString());
                    }
                    apiUrl += param;
                }
                // delete请求暂不处理
                else if (!(httpPost instanceof HttpDelete)) {
                    // K-V形式
                    if (params instanceof Map) {
                        Map<String, Object> paramsConvert = (Map<String, Object>) params;

                        List<NameValuePair> pairList = new ArrayList<>(paramsConvert.size());
                        for (Map.Entry<String, Object> entry : paramsConvert.entrySet()) {
                            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                            pairList.add(pair);
                        }
                        ((HttpEntityEnclosingRequestBase) httpPost)
                                .setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
                    }
                    // json格式
                    else {
                        StringEntity stringEntity = new StringEntity(params.toString(), "UTF-8");
                        stringEntity.setContentEncoding("UTF-8");
                        stringEntity.setContentType("application/json");
                        ((HttpEntityEnclosingRequestBase) httpPost).setEntity(stringEntity);
                    }
                }
            }
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
            System.out.println("starting doPost ---》");
            response = httpClient.execute(httpPost);
            System.out.println("ending doPost ---》" + response.getAllHeaders());
            // int statusCode = response.getStatusLine().getStatusCode();
            // if (statusCode != HttpStatus.SC_OK) {
            // return null;
            // }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (type == 1) {
                    System.out.println("return value -->" + entity.getContent());
                    return (T) entity.getContent();
                }
                else {
                    System.out.println("return value -->" + EntityUtils.toString(entity, "UTF-8"));
                    return (T) EntityUtils.toString(entity, "UTF-8");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("return value -->" + null);
        return null;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
            {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier()
            {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}
