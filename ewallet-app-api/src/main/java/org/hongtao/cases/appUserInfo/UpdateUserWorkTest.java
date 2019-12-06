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
import org.hongtao.model.appUserInfo.UserAddressCase;
import org.hongtao.model.appUserInfo.UserInfoCase;
import org.hongtao.model.appUserInfo.UserSalaryCase;
import org.hongtao.model.appUserInfo.UserWorkCase;
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

import static org.hongtao.config.TestConfig.appUserLoginUrl;
import static org.hongtao.config.TestConfig.updateUserWorkUrl;

public class UpdateUserWorkTest {

    private static Logger logger = Logger.getLogger(String.valueOf(UpdateUserInfoTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        updateUserWorkUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERWORK);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }

    @Test(description = "updateUserWork")
    public void updateUserWork() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        SqlSession session1 = DatabaseUtil.getSqlSession();
        UserWorkCase userWorkCase = session1.selectOne("userWorkCase", 7);

        SqlSession session2 = DatabaseUtil.getSqlSession();
        UserAddressCase userAddressCase1 = session2.selectOne("userAddressCase", 9);
        UserAddressCase userAddressCase2 = session2.selectOne("userAddressCase", 10);

        SqlSession session3 = DatabaseUtil.getSqlSession();
        UserSalaryCase userSalaryCase = session3.selectOne("userSalaryCase", 7);


        JSONObject resultJson = getJsonResult(userWorkCase,userAddressCase1,userAddressCase2,userSalaryCase,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("AppUserLogin response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }


    private JSONObject getJsonResult(UserWorkCase userWorkCase, UserAddressCase userAddressCase1, UserAddressCase userAddressCase2, UserSalaryCase userSalaryCase , String token) throws IOException {

        //第一步：创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost post = new HttpPost(updateUserWorkUrl);
        logger.info("updateUserInfoUrl:" + updateUserWorkUrl);
        JSONObject param = new JSONObject();
        post.setHeader("Authorization",token);
        param.put("addressDetail", userAddressCase1.getAddressDetail());
        param.put("addressDetailOfHome", userAddressCase2.getAddressDetail());
        param.put("companyName", userWorkCase.getCompanyName());
        param.put("companyPhone", userAddressCase1.getCompanyPhone());
        param.put("county", userAddressCase1.getCounty());
        param.put("countyOfHome",userAddressCase1.getCounty());
        param.put("district", userAddressCase1.getDistrict());
        param.put("districtOfHome", userAddressCase2.getDistrict());
        param.put("educationDegree", userWorkCase.getEducationDegree());
        param.put("payday", userWorkCase.getPayday());
        param.put("position", userWorkCase.getPosition());
        param.put("profession",userWorkCase.getProfession());
        param.put("salary", userSalaryCase.getSalary());
        param.put("school", userWorkCase.getSchool());
        param.put("state", userAddressCase1.getState());
        param.put("stateOfHome", userAddressCase2.getState());
        param.put("town", userAddressCase1.getTown());
        param.put("townOfHome", userAddressCase2.getTown());
        param.put("workYear", userWorkCase.getWorkYear());


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
