package org.hongtao.cases.appUserInfo;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.cases.appConfig.GetLatestVersionTest;
import org.hongtao.cases.appRegister.AppUserLoginTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
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

import static org.hongtao.config.TestConfig.getUserContactUrl;
import static org.hongtao.config.TestConfig.appUserLoginUrl;

public class GetUserContactTest {
    private static Logger logger = Logger.getLogger(String.valueOf(GetLatestVersionTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        getUserContactUrl = ConfigFile.getUrl(InterfaceName.GETUSERCONTACT);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }


    @Test(description = "getUserContact")
    public void getUserContact() throws IOException, URISyntaxException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        JSONObject resultJson = getJsonResult(token);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        String data=resultJson.getString ( "data" ) ;
        logger.info("data:"+data);
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    private JSONObject getJsonResult(String token ) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(getUserContactUrl);

        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 通过址默认配置创建一个httpClient实例
        httpClient = HttpClients.createDefault();

        httpGet.setHeader("User-Agent","Retrofit-Sample-App");
        httpGet.setHeader("accept-encoding","gzip");
        httpGet.setHeader("content-type","application/json");
        httpGet.setHeader("Authorization",token);
        httpGet.setHeader("deviceOs","android");
        httpGet.setHeader("appVersion","1");
        httpGet.setHeader("channel","MoNeed");
        httpGet.setHeader("UDID","865547044428625");
        httpGet.setHeader("mobile","1123070484");

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
        // 通过EntityUtils中的toString方法将结果转换为字符串
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);

        return jsonObject;

    }


}
