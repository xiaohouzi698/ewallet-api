package org.hongtao.cases.appRegister;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.model.appRegister.UserIsRegisteredRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import com.alibaba.fastjson.JSONObject;


import static org.hongtao.config.TestConfig.userIsRegisteredUrl;

public class UserIsRegisteredTest {

    private static Logger logger = Logger.getLogger(String.valueOf(UserIsRegisteredTest.class));

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        userIsRegisteredUrl = ConfigFile.getUrl(InterfaceName.USERISREGISTERED);

    }


    @Test(description = "registerReturnToken")
    public void registerReturnToken() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 6);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("主流程测试-注册");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        String data=resultJson.getString ( "data" ) ;

        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
        //Assert.assertEquals( data,"{\"setPassword\":false,\"registered\":false}");

    }


    @Test(description = "判断1123070401用户是否注册")
    public void mobileExistence() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 6);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("判断1123070401用户是否注册");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        String data=resultJson.getString ( "data" ) ;

        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
        Assert.assertEquals( data,"{\"setPassword\":true,\"registered\":true}");

    }

    @Test(description = "判断1123070402用户是否注册",enabled = false)
    public void mobileNotExistence() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 2);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("判断1123070402用户是否注册");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "手机号太短",enabled = false)
    public void mobileTooShort() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 3);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("手机号太短");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,9400 );
        //Assert.assertEquals( message,"success");
    }

    @Test(description = "手机号太长",enabled = false)
    public void mobileTooLong() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 4);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("手机号太长");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,9400 );
        //Assert.assertEquals( message,"success");
    }

    @Test(description = "手机号为空",enabled = false)
    public void mobileIsEmpty() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UserIsRegisteredRequestParameter userIsRegisteredRequestParameter = new UserIsRegisteredRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 5);
        userIsRegisteredRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("手机号为空");
        logger.info(userIsRegisteredRequestParameter.toString());
        JSONObject resultJson = getJsonResult(userIsRegisteredRequestParameter);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,9400 );
        //Assert.assertEquals("success", message);
    }

    private JSONObject getJsonResult(UserIsRegisteredRequestParameter userIsRegisteredRequestParameter) throws IOException, URISyntaxException {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";

        URIBuilder uriBuilder = new URIBuilder(userIsRegisteredUrl);
        uriBuilder.addParameter("mobile", userIsRegisteredRequestParameter.getMobile());
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
        //JSONArray jsonArray = new JSONArray(result);

        return jsonObject;

    }
}
