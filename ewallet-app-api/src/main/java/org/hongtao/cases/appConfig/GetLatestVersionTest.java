package org.hongtao.cases.appConfig;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appConfig.GetLatestVersionRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.getLatestVersionUrl;

public class GetLatestVersionTest {

    private static Logger logger = Logger.getLogger(String.valueOf(GetLatestVersionTest.class));

    @BeforeTest(groups = "appConfigTest", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        getLatestVersionUrl = ConfigFile.getUrl(InterfaceName.GETLATESTVERSION);

    }

    @Test(description = "正常的版本号")
    public void getLatestVersionTest() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetLatestVersionRequestParameter getLatestVersionRequestParameter = session.selectOne("getLatestVersionRequestParameter", 1);
        logger.info("正常的版本号");
        logger.info(getLatestVersionRequestParameter.toString());
        HttpUtil HttpUtil=new HttpUtil();
        JSONObject resultJson = HttpUtil.httpGet(getLatestVersionUrl);
        logger.info(resultJson.toString());
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "版本号不存在")
    public void versionNotExistence() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetLatestVersionRequestParameter getLatestVersionRequestParameter = session.selectOne("getLatestVersionRequestParameter", 2);
        logger.info("版本号不存在");
        logger.info(getLatestVersionRequestParameter.toString());
        HttpUtil HttpUtil=new HttpUtil();
        JSONObject resultJson = HttpUtil.httpGet(getLatestVersionUrl);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "版本号格式错误",enabled = false)
    public void versionIsFalse() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetLatestVersionRequestParameter getLatestVersionRequestParameter = session.selectOne("getLatestVersionRequestParameter", 3);
        logger.info("版本号格式错误");
        logger.info(getLatestVersionRequestParameter.toString());
        HttpUtil HttpUtil=new HttpUtil();
        JSONObject resultJson = HttpUtil.httpGet(getLatestVersionUrl);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,9400 );
        //Assert.assertEquals( message,"success");
    }


    @Test(description = "版本号为空",enabled = false)
    public void versionIsEmpty() throws IOException, URISyntaxException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetLatestVersionRequestParameter getLatestVersionRequestParameter = session.selectOne("getLatestVersionRequestParameter", 4);
        logger.info("版本号为空");
        logger.info(getLatestVersionRequestParameter.toString());
        HttpUtil HttpUtil=new HttpUtil();
        JSONObject resultJson = HttpUtil.httpGet(getLatestVersionUrl);
        logger.info(resultJson.toString());

        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,9400 );
        //Assert.assertEquals("success", message);
    }


}
