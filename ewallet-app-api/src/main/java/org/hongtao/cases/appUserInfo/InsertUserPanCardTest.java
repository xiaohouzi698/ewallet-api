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
import org.hongtao.model.appUserInfo.UserPanCardCase;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.RandomUtil;
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
import static org.hongtao.config.TestConfig.insertUserPanCardUrl;

public class InsertUserPanCardTest {
    private static Logger logger = Logger.getLogger(String.valueOf(InsertUserAadhaarCardTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        insertUserPanCardUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERPANCARD);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }


    @Test(description = "insertUserPanCard")
    public void insertUserPanCard() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, ParseException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        SqlSession session = DatabaseUtil.getSqlSession();
        UserPanCardCase userPanCardCase = session.selectOne("userPanCardCase", 12);

        JSONObject resultJson = getJsonResult(userPanCardCase,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("insertUserAadhaarCard response data:"+data);

        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }



    private JSONObject getJsonResult(UserPanCardCase userPanCardCase, String token) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, ParseException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(insertUserPanCardUrl);
        logger.info("appUserLoginUrl:" + insertUserPanCardUrl);
        JSONObject param = new JSONObject();
        post.setHeader("Authorization",token);

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        String reformattedStr = myFormat.format(fromUser.parse( userPanCardCase.getBirthday()));

        param.put("birthday",reformattedStr);
        param.put("fathersName", userPanCardCase.getFathersName());
        param.put("holdsFullName", userPanCardCase.getHoldsFullName());
        param.put("identifyStatus", 1);
        param.put("matchScore",userPanCardCase.getMatchScore());
        param.put("matchStatus", 0);
        param.put("panUrlBack", userPanCardCase.getPanUrlBack());
        param.put("panUrlFront", userPanCardCase.getPanUrlFront());

        RandomUtil randomUtil = new RandomUtil();
        String permanentNo=randomUtil.getRandomString(10);
        param.put("permanentNo", permanentNo);
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