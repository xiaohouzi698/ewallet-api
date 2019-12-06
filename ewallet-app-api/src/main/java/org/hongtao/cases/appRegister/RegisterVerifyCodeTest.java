package org.hongtao.cases.appRegister;

import com.alibaba.fastjson.JSONObject;
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
import org.hongtao.model.appRegister.RegisterVerifyCodeRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.registerVerifyCodeUrl;

public class RegisterVerifyCodeTest {
    private static Logger logger = Logger.getLogger(String.valueOf(RegisterVerifyCodeTest.class));

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        registerVerifyCodeUrl = ConfigFile.getUrl(InterfaceName.REGISTERVERIFYCODE);

    }

    @Test(description = "registerReturnToken",enabled = false,threadPoolSize = 3, invocationCount = 10,  timeOut = 10000)
    public void registerReturnToken() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        RegisterVerifyCodeRequestParameter registerVerifyCodeRequestParameter = new RegisterVerifyCodeRequestParameter();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 6);
        registerVerifyCodeRequestParameter.setMobile(appUserLoginRequestParameter.getMobile());
        logger.info("主流程测试-注册");
        logger.info(registerVerifyCodeRequestParameter.toString());
        JSONObject resultJson = getJsonResult(registerVerifyCodeRequestParameter);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        String data=resultJson.getString ( "data" ) ;

        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
        //Assert.assertEquals( data,"59ada92d-09b3-4b2a-817e-f0bdd3ca1e5f");

    }


    JSONObject getJsonResult(RegisterVerifyCodeRequestParameter registerVerifyCodeRequestParameter) throws IOException, URISyntaxException {

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
        //JSONArray jsonArray = new JSONArray(result);

        return jsonObject;

    }





}
