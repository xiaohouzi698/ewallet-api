package org.hongtao.cases.appRegister;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.EncryptUtil;
import org.hongtao.utils.HttpUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.appUserLoginUrl;

public class AppUserLoginTest {
    private static Logger logger = Logger.getLogger(String.valueOf(AppUserLoginTest.class));

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);

    }

    @Test(description = "正确的密码")
    public void truePassword() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 6);
        logger.info("正确的密码");
        JSONObject resultJson = getJsonResult(appUserLoginRequestParameter);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("AppUserLogin response data:"+data);

        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "错误的密码",enabled = false)
    public void errorPassword() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 2);
        JSONObject resultJson = getJsonResult(appUserLoginRequestParameter);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "空的密码",enabled = false)
    public void emptyPassword() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 3);
        JSONObject resultJson = getJsonResult(appUserLoginRequestParameter);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "空的手机",enabled = false)
    public void emptyMobile() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = session.selectOne("appUserLoginRequestParameter", 4);
        JSONObject resultJson = getJsonResult(appUserLoginRequestParameter);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }


    private JSONObject getJsonResult(AppUserLoginRequestParameter appUserLoginRequestParameter) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        HttpUtil httpUtil = new HttpUtil();
        JSONObject param = new JSONObject();
        String password = appUserLoginRequestParameter.getPassword();
        String encryptedPassword = "";
        EncryptUtil encryptUtil = new EncryptUtil();
        encryptedPassword = encryptUtil.rasEncrypt2Base64(password);
        logger.info("加密前:" + password);
        logger.info("\n加密后:" + encryptedPassword);
        param.put("mobile", appUserLoginRequestParameter.getMobile());
        param.put("encryptedPassword", encryptedPassword);

        JSONObject resultJson = httpUtil.httpPost(appUserLoginUrl,param);
        return resultJson;
    }



    public String returnBearToken(AppUserLoginRequestParameter appUserLoginRequestParameter) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        JSONObject jsonObject = getJsonResult(appUserLoginRequestParameter);

        String bearToken=jsonObject.getString("data");

        return bearToken;
    }

}
