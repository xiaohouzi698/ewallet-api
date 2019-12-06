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
import org.hongtao.model.appUserInfo.UserAadhaarCardCase;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.appUserLoginUrl;
import static org.hongtao.config.TestConfig.insertUserAadhaarCardUrl;

public class InsertUserAadhaarCardTest {
    private static Logger logger = Logger.getLogger(String.valueOf(InsertUserAadhaarCardTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        insertUserAadhaarCardUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERAADHAARCARD);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }


    @Test(description = "insertUserAadhaarCard")
    public void insertUserAadhaarCard() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, ParseException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        SqlSession session = DatabaseUtil.getSqlSession();
        UserAadhaarCardCase userAadhaarCardCase = session.selectOne("userAadhaarCardCase", 16);

        JSONObject resultJson = getJsonResult(userAadhaarCardCase,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("insertUserAadhaarCard response data:"+data);

        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }



    private JSONObject getJsonResult(UserAadhaarCardCase userAadhaarCardCase, String token) throws IOException, ParseException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(insertUserAadhaarCardUrl);
        logger.info("appUserLoginUrl:" + insertUserAadhaarCardUrl);
        JSONObject param = new JSONObject();
        post.setHeader("Authorization",token);
        param.put("aadhaarAccount", userAadhaarCardCase.getAadhaarAccount());
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = myFormat.format(fromUser.parse(userAadhaarCardCase.getAadhaarBirthday()));

        param.put("aadhaarBirthday", reformattedStr);
        param.put("aadhaarGender", "MALE");
        param.put("aadhaarName", userAadhaarCardCase.getAadhaarName());
        param.put("aadhaarUrlBack", userAadhaarCardCase.getAadhaarUrlBack());
        param.put("aadhaarUrlFront", userAadhaarCardCase.getAadhaarUrlFront());
        param.put("identifyStatus", 1);
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
