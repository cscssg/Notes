package top.bluewort.Notes.base_utils.X006_http_request;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    private static HttpClientUtil instance = null;
    private HttpClientUtil(){}
    public static HttpClientUtil getInstance(){
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @throws IOException
     */
    public String sendHttpPost(String httpUrl) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     * @throws IOException
     */
    public String sendHttpPost(String httpUrl, String params) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(stringEntity);
        return sendHttpPost(httpPost);
    }

    public static JSONObject sendHttpPost(String httpUrl, JSONObject params) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity stringEntity = new StringEntity(params.toString(), "UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        return JSONObject.parseObject(sendHttpPost(httpPost));
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     * @throws IOException
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps) throws IOException {
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        return sendHttpPost(httpPost);
    }


    /**
     * 发送 post请求（带文件）
     * @param httpUrl 地址
     * @param maps 参数
     * @param fileLists 附件
     * @throws IOException
     */
//    public String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) throws IOException {
//        HttpPost httpPost = new HttpPost(httpUrl);
//        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
//        for (String key : maps.keySet()) {
//            meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
//        }
//        for(File file : fileLists) {
//            FileBody fileBody = new FileBody(file);
//            meBuilder.addPart("files", fileBody);
//        }
//        HttpEntity reqEntity = meBuilder.build();
//        httpPost.setEntity(reqEntity);
//        return sendHttpPost(httpPost);
//    }

    /**
     * 发送Post请求
     * @param httpPost
     * @return
     * @throws IOException
     */
    private static String sendHttpPost(HttpPost httpPost) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            throw  e;
        } finally {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
        }
        return responseContent;
    }

    /**
     * 发送 get请求
     * @param httpUrl
     * @throws IOException
     */
    public String sendHttpGet(String httpUrl) throws IOException {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求Https
     * @param httpUrl
     * @throws IOException
     */
    public static String sendHttpsGet(String httpUrl) throws IOException {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送Get请求
     * @param httpGet
     * @return
     * @throws IOException
     */
    private String sendHttpGet(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
        } finally {
            // 关闭连接,释放资源
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }

        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     * @param httpGet
     * @return
     * @throws IOException
     */
    private static String sendHttpsGet(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();

            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
        } finally {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
        }
        return responseContent;
    }
    public static void main(String[] args) {
        String param = "{\n" +
                "  \"from\":\"cqy@juncdt.com\",\n" +
                "  \"to\":[{\n" +
                "    \"leaderName\":\"罗辑\",\n" +
                "    \"address\":\"cqyao@juncdt.com\"\n" +
                "  }],\n" +
                "  \"subject\":\"协鑫集成-大数据平台月报表\",\n" +
                "  \"biUrl\":\"https://das.base.shuju.aliyun.com/token3rd/dashboard/view/pc.htm?pageId=f365bc70-a741-4967-be92-56e01742d502&accessToken=68156faea142956e9da6adb620e8c590\",\n" +
                "  \"date\":\"2018-07\"\n" +
                "}";
        try {
            JSONObject result = sendHttpPost("http://127.0.0.1:7001/api/emailSend", JSONObject.parseObject(param));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
