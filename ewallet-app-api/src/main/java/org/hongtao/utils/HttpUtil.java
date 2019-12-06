package org.hongtao.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;



public class HttpUtil {
    private static Logger logger = Logger.getLogger(String.valueOf(HttpUtil.class));


    public JSONObject httpGet(String url) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(url);
        logger.info("http-get请求路径："+url);
        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        httpGet.setHeader("User-Agent","Retrofit-Sample-App");
        httpGet.setHeader("accept-encoding","gzip");
        httpGet.setHeader("content-type","application/json");
        httpGet.setHeader("deviceOs","android");
        httpGet.setHeader("appVersion","1");
        httpGet.setHeader("channel","MoNeed");
        httpGet.setHeader("UDID","865547044428625");

        // 通过址默认配置创建一个httpClient实例
        httpClient = HttpClients.createDefault();

        // 设置配置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 请求超时时间
                .setSocketTimeout(60000)// 数据读取超时时间
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        logger.info("http-get请求参数："+httpGet.toString());
        // 执行get请求得到返回对象
        response = httpClient.execute(httpGet);
        // 通过返回对象获取返回数据
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        logger.info("http-get请求返回结果："+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }



    public JSONObject httpPost(String url, JSONObject param) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost httpPost = new HttpPost(url);
        logger.info("http-post请求路径："+url);

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        logger.info("http-post请求参数："+httpPost.toString());
        String result; // 存放返回结果

        //第四步：发送HttpPost请求，获取返回值
        result = httpClient.execute(httpPost, responseHandler);
        logger.info("http-post请求结果："+result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        return jsonObject;
    }

}
