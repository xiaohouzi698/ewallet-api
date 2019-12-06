package org.hongtao.cases.appRegister;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.model.appRegister.PutUserPasswordRequestParameter;
import org.hongtao.model.appRegister.RegisterVerifyCodeRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.EncryptUtil;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.putUserPasswordUrl;
import static org.hongtao.config.TestConfig.registerVerifyCodeUrl;

public class PutUserPasswordTest {
    private static Logger logger = Logger.getLogger(String.valueOf(PutUserPasswordTest.class));

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        putUserPasswordUrl = ConfigFile.getUrl(InterfaceName.PUTUSERPASSWORD);
        registerVerifyCodeUrl = ConfigFile.getUrl(InterfaceName.REGISTERVERIFYCODE);    }

    @Test(description = "registerAndPutUserPassword")
    public void registerAndPutUserPassword() throws IOException, URISyntaxException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        SqlSession session = DatabaseUtil.getSqlSession();
        RegisterVerifyCodeRequestParameter registerVerifyCodeRequestParameter = new RegisterVerifyCodeRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 6);
        registerVerifyCodeRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());

        String tempToken=getTempToken(registerVerifyCodeRequestParameter) ;
        String mobile=appUserLoginRequestParameter.getMobile();

        //创造加密密码
        String password = "123456";
        String encryptedPassword = "";
        EncryptUtil encryptUtil = new EncryptUtil();
        encryptedPassword = encryptUtil.rasEncrypt2Base64(password);

        PutUserPasswordRequestParameter putUserPasswordRequestParameter = new PutUserPasswordRequestParameter();
        putUserPasswordRequestParameter.setMobile(mobile);
        putUserPasswordRequestParameter.setTmpToken(tempToken);
        putUserPasswordRequestParameter.setEncryptedString(encryptedPassword);

        JSONObject jsonResult=getJsonResult(putUserPasswordRequestParameter);
        logger.info("\njsonResult:" + jsonResult.toString());

    }



    private JSONObject getJsonResult(PutUserPasswordRequestParameter putUserPasswordRequestParameter) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(putUserPasswordUrl);
        uriBuilder.addParameter("mobile", putUserPasswordRequestParameter.getMobile());
        uriBuilder.addParameter("encryptedString", putUserPasswordRequestParameter.getEncryptedString());
        uriBuilder.addParameter("tmpToken", putUserPasswordRequestParameter.getTmpToken());
        uriBuilder.addParameter("channel", "MoNeed");
        // 根据带参数的URI对象构建GET请求对象
        HttpPut httpPut = new HttpPut(uriBuilder.build());

        // 通过址默认配置创建一个httpClient实例
        httpClient = HttpClients.createDefault();

        // 设置配置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 请求超时时间
                .setSocketTimeout(60000)// 数据读取超时时间
                .build();
        // 为httpGet实例设置配置
        httpPut.setConfig(requestConfig);
        // 执行get请求得到返回对象
        response = httpClient.execute(httpPut);
        // 通过返回对象获取返回数据
        //HttpEntity entity = response.getEntity();
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        //JSONArray jsonArray = new JSONArray(result);

        return jsonObject;
    }


    private String getTempToken(RegisterVerifyCodeRequestParameter registerVerifyCodeRequestParameter) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(registerVerifyCodeUrl);
        uriBuilder.addParameter("mobile", registerVerifyCodeRequestParameter.getMobile());
        uriBuilder.addParameter("code", registerVerifyCodeRequestParameter.getCode());
        uriBuilder.addParameter("channel", "MoNeed");

        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 通过址默认配置创建一个httpClient实例
        httpClient = HttpClients.createDefault();

        // 设置配置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 请求超时时间
                .setSocketTimeout(60000)// 数据读取超时时间
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        // 执行get请求得到返回对象
        response = httpClient.execute(httpGet);
        // 通过返回对象获取返回数据
        //HttpEntity entity = response.getEntity();
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        logger.info("\njsonObject:" + jsonObject.toString());
        String tempToken=jsonObject.getString ( "data" ) ;
        //JSONArray jsonArray = new JSONArray(result);
        logger.info("\ntempToken:" + tempToken);

        return tempToken;

    }
}
