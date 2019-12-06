package org.hongtao.cases.orderWalletController;

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
import org.apache.ibatis.session.SqlSession;
import org.hongtao.cases.appRegister.UserIsRegisteredTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appConfig.GetLatestVersionRequestParameter;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.EncryptUtil;
import org.testng.Assert;
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

import static org.hongtao.config.TestConfig.*;

public class GetAppIndexResultTest {

    private static Logger logger = Logger.getLogger(String.valueOf(GetAppIndexResultTest.class));

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        getAppIndexResultUrl = ConfigFile.getUrl(InterfaceName.GETAPPINDEXRESULT);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }


    @Test(description = "getAppIndexResultTest")
    public void getAppIndexResultTest() throws IOException, URISyntaxException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 1);
        String token=getToken(appUserLoginRequestParameter);
        JSONObject resultJson = getJsonResult(token);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    private JSONObject getJsonResult( String token ) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(getAppIndexResultUrl);

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
        httpGet.setHeader("Authorization",token);
        // 执行get请求得到返回对象
        response = httpClient.execute(httpGet);
        // 通过返回对象获取返回数据
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        return jsonObject;

    }

    private String getToken(AppUserLoginRequestParameter appUserLoginRequestParameter) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(appUserLoginUrl);
        logger.info("appUserLoginUrl:" + appUserLoginUrl);
        JSONObject param = new JSONObject();
        String password = appUserLoginRequestParameter.getPassword();
        String encryptedPassword = "";
        EncryptUtil encryptUtil = new EncryptUtil();
        encryptedPassword = encryptUtil.rasEncrypt2Base64(password);
        logger.info("加密前:" + password);
        logger.info("\n加密后:" + encryptedPassword);

        param.put("mobile", appUserLoginRequestParameter.getMobile());
        param.put("encryptedPassword", encryptedPassword);
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        String result; // 存放返回结果

        //第四步：发送HttpPost请求，获取返回值
        result = httpClient.execute(post, responseHandler);
        logger.info("result:" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token=jsonObject.getString ( "data" ) ;
        return token;
    }



}
