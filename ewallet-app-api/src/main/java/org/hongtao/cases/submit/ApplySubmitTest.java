package org.hongtao.cases.submit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.cases.appRegister.AppUserLoginTest;
import org.hongtao.cases.appUserInfo.InsertUserAadhaarCardTest;
import org.hongtao.cases.appUserInfo.UploadFileTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.model.appUserInfo.InsertUserFilesCase;
import org.hongtao.model.appUserInfo.UserPanCardCase;
import org.hongtao.model.submit.*;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.RandomUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.applySubmitUrl;
import static org.hongtao.config.TestConfig.appUserLoginUrl;

public class ApplySubmitTest {
    private static Logger logger = Logger.getLogger(String.valueOf(InsertUserAadhaarCardTest.class));

    @BeforeTest(groups = "submit", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        applySubmitUrl = ConfigFile.getUrl(InterfaceName.APPLYSUBMIT);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
    }

    @Test(description = "applySubmit")
    public void applySubmit() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {

        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);

        ApplySubmitCase applySubmitCase = new ApplySubmitCase();

        SubmitApplyDto submitApplyDto = new SubmitApplyDto();
        submitApplyDto.setApplyAmount("6000");
        submitApplyDto.setProductId("1");
        submitApplyDto.setProductName("钱包产品");

        applySubmitCase.setSubmitApplyDto(submitApplyDto);

        AppDeviceDto appDeviceDto = new AppDeviceDto();
        appDeviceDto.setAltitude("0");
        appDeviceDto.setBattery("35");
        RandomUtil randomUtil = new RandomUtil();
        String imiId=randomUtil.getRandomInt(16);
        appDeviceDto.setImiId(imiId);

        String ip=randomUtil.getRandomIp();
        appDeviceDto.setIp(ip);
        appDeviceDto.setLongitude(0.0);
        appDeviceDto.setGpsAddress("111.222,111.111");
        appDeviceDto.setManufacturer("HUAWEI");
        appDeviceDto.setModel("BKK-AL10");
        appDeviceDto.setNetworkType(10);

        //调试模式
        //appDeviceDto.setPhoneModel(1);
        //非调试模式
        appDeviceDto.setPhoneModel(0);

        List<String> applicationName = new ArrayList<String>();
        applicationName.add("iwangzhe.testcustomview");
        applicationName.add("iwangzhe.taobao");

        appDeviceDto.setApplicationNames(applicationName);
        AppCrawlDataDto appCrawlDataDto = new AppCrawlDataDto();
        appCrawlDataDto.setAppDeviceDto(appDeviceDto);


        String getMobile = appUserLoginRequestParameter.getMobile();
        applySubmitCase.setMobile(getMobile);
        applySubmitCase.setUserId(1);
        applySubmitCase.setAppVersion("1");
        applySubmitCase.setDeviceOs("android");
        applySubmitCase.setChannel("MoNeed");
        applySubmitCase.setApplyNo(1);
        AppAddressBookDtoList appAddressBookDtoList = new AppAddressBookDtoList();
        appAddressBookDtoList.setMobile("1123070401");
        appAddressBookDtoList.setName("1123070401");

        List<AppAddressBookDtoList> appAddressBookList = new ArrayList<AppAddressBookDtoList>();
        appAddressBookList.add(appAddressBookDtoList);

        AppCallRecordDtoList appCallRecordDtoList = new AppCallRecordDtoList();
        appCallRecordDtoList.setType(0);
        appCallRecordDtoList.setDate("1563861484763");
        appCallRecordDtoList.setDuration(0);
        appCallRecordDtoList.setMobile("1123070423");
        appCallRecordDtoList.setPerson("lover");

        List<AppCallRecordDtoList> appCallRecordList = new ArrayList<AppCallRecordDtoList>();
        appCallRecordList.add(appCallRecordDtoList);


        AppMessagesDtoList appMessagesDtoList = new AppMessagesDtoList();
        appMessagesDtoList.setAddress("11223344");
        appMessagesDtoList.setBody("112233445566");
        appMessagesDtoList.setDate("1563861484763");
        appMessagesDtoList.setPerson("liuhuan");
        appMessagesDtoList.setRead("1");
        appMessagesDtoList.setType("1");

        List<AppMessagesDtoList> appMessagesList = new ArrayList<AppMessagesDtoList>();
        appMessagesList.add(appMessagesDtoList);


        appCrawlDataDto.setAppAddressBookDtoList(appAddressBookList);
        appCrawlDataDto.setAppCallRecordDtoList(appCallRecordList);
        appCrawlDataDto.setAppMessagesDtoList(appMessagesList);
        applySubmitCase.setAppCrawlDataDto(appCrawlDataDto);


        String s = JSON.toJSONString(applySubmitCase);
        logger.info("toJsonString()方法：s=" + s);


        AppUserLoginTest appUserLoginTest = new AppUserLoginTest();
        String token = appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        logger.info("ip -----------" + ip);

        JSONObject resultJson = getJsonResult(s, token);
        int code = resultJson.getInteger("code");    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");

        logger.info("insertUserFiles response data:" + data);

        String message = resultJson.getString("message");
        Assert.assertEquals(code, 60001);
        Assert.assertEquals(message, "success");
    }


    private JSONObject getJsonResult(String json, String token) throws IOException {


        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(applySubmitUrl);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", token);
        String result = "";
        RandomUtil randomUtil = new RandomUtil();
        String ip=randomUtil.getRandomIp();
        post.addHeader("x-forwarded-for",ip);

        StringEntity s = new StringEntity(json.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        post.setEntity(s);

        // 发送请求
        HttpResponse httpResponse = client.execute(post);

        // 获取响应输入流
        InputStream inStream = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inStream, "utf-8"));
        StringBuilder strber = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
            strber.append(line + "\n");
        inStream.close();

        result = strber.toString();
        System.out.println(result);
        logger.info("applySubmit result response data:" + result);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            System.out.println("请求服务器成功，做相应处理");

        } else {

            System.out.println("请求服务端失败");

        }

        JSONObject jsobject = JSONObject.parseObject(result);
        return jsobject;
    }
}
