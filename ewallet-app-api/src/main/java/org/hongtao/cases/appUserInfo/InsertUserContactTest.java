package org.hongtao.cases.appUserInfo;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.cases.appRegister.AppUserLoginTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.model.appUserInfo.InsertUserContactCase;
import org.hongtao.model.appUserInfo.InsertUserFilesCase;
import org.hongtao.model.appUserInfo.UserInfoCase;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
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

import static org.hongtao.config.TestConfig.insertUserContactUrl;
import static org.hongtao.config.TestConfig.appUserLoginUrl;

public class InsertUserContactTest {
    private static Logger logger = Logger.getLogger(String.valueOf(InsertUserAadhaarCardTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        insertUserContactUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERCONTACT);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }

    @Test(description = "insertUserContact")
    public void insertUserContact() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        SqlSession session = DatabaseUtil.getSqlSession();
        InsertUserContactCase insertUserContactCase1 = session.selectOne("insertUserContactCase", 29);
        InsertUserContactCase insertUserContactCase2 = session.selectOne("insertUserContactCase", 30);
        UserInfoCase userInfoCase = session.selectOne("userInfoCase", 10000057);

        JSONObject resultJson = getJsonResult(insertUserContactCase1,insertUserContactCase2,userInfoCase,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("insertUserFiles response data:"+data);

        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }



    private JSONObject getJsonResult(InsertUserContactCase insertUserContactCase1,InsertUserContactCase insertUserContactCase2, UserInfoCase userInfoCase, String token) throws IOException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(insertUserContactUrl);
        logger.info("insertUserFilesUrl:" + insertUserContactUrl);
        JSONObject param = new JSONObject();
        post.setHeader("Authorization",token);
        param.put("facebookAccount", userInfoCase.getFacebookAccount());
        param.put("name", insertUserContactCase1.getName());
        param.put("name2", insertUserContactCase2.getName());

        param.put("phone", insertUserContactCase1.getPhone());
        param.put("phone2", insertUserContactCase2.getPhone());

        param.put("relation", "Parent");
        param.put("relation2", "Colleague");

        param.put("skypeAccount", userInfoCase.getSkypeAccount());
        param.put("whatsappAccount", userInfoCase.getWhatsappAccount());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        String result; // 存放返回结果

        //第四步：发送HttpPost请求，获取返回值
        result = httpClient.execute(post, responseHandler);
        logger.info("result:" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        return jsonObject;
    }




}
