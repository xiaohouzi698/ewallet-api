package org.hongtao.cases.appUserInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.hongtao.cases.appRegister.AppUserLoginTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.model.appRegister.AppUserLoginRequestParameter;
import org.hongtao.model.appUserInfo.UploadFileRequestParameter;
import org.hongtao.utils.ConfigFile;
import org.hongtao.utils.DatabaseUtil;
import org.hongtao.utils.EncryptUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.appUserLoginUrl;
import static org.hongtao.config.TestConfig.uploadFileUrl;

public class UploadFileTest {
    private static Logger logger = Logger.getLogger(String.valueOf(UploadFileTest.class));

    @BeforeTest(groups = "appUserInfo", description = "测试准备工作，配置测试路径")
    public void beforeTest() {
        uploadFileUrl = ConfigFile.getUrl(InterfaceName.UPLOADFILE);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);

    }

    @Test(description = "uploadFaceImage")
    public void uploadFaceImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter = session.selectOne("uploadFileRequestParameter", 1);
        JSONObject resultJson = getJsonResult(uploadFileRequestParameter,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "uploadAadhaarImage")
    public void uploadAadhaarImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter1 = session.selectOne("uploadFileRequestParameter", 2);
        UploadFileRequestParameter uploadFileRequestParameter2 = session.selectOne("uploadFileRequestParameter", 3);

        JSONObject resultJson = uploadTwoImage(uploadFileRequestParameter1,uploadFileRequestParameter2,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }


    @Test(description = "uploadPancardImage")
    public void uploadPancardImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter1 = session.selectOne("uploadFileRequestParameter", 4);
        UploadFileRequestParameter uploadFileRequestParameter2 = session.selectOne("uploadFileRequestParameter", 5);

        JSONObject resultJson = uploadTwoImage(uploadFileRequestParameter1,uploadFileRequestParameter2,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "uploadBankStatementImage")
    public void uploadBankStatementImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter = session.selectOne("uploadFileRequestParameter", 6);
        JSONObject resultJson = getJsonResult(uploadFileRequestParameter,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }


    @Test(description = "uploadWorkCredentialImage")
    public void uploadWorkCredentialImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter = session.selectOne("uploadFileRequestParameter", 7);
        JSONObject resultJson = getJsonResult(uploadFileRequestParameter,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "uploadSalarySlipsImage")
    public void uploadSalarySlipsImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);

        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter = session.selectOne("uploadFileRequestParameter", 8);
        JSONObject resultJson = getJsonResult(uploadFileRequestParameter,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    @Test(description = "uploadBankcardImage")
    public void uploadBankcardImage() throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        SqlSession sessionGetToken = DatabaseUtil.getSqlSession();
        AppUserLoginRequestParameter appUserLoginRequestParameter = sessionGetToken.selectOne("appUserLoginRequestParameter", 6);
        AppUserLoginTest appUserLoginTest=new AppUserLoginTest();
        String token=appUserLoginTest.returnBearToken(appUserLoginRequestParameter);
        SqlSession session = DatabaseUtil.getSqlSession();
        UploadFileRequestParameter uploadFileRequestParameter = session.selectOne("uploadFileRequestParameter", 9);
        JSONObject resultJson = getJsonResult(uploadFileRequestParameter,token);
        int code= resultJson.getInteger ( "code" ) ;    //  这里的code得到的数据就是2000.
        String data = resultJson.getString("data");
        logger.info("uploadFaceImage response data:"+data);
        String message=resultJson.getString ( "message" ) ;
        Assert.assertEquals(code,2000 );
        Assert.assertEquals( message,"success");
    }

    public JSONObject getJsonResult(UploadFileRequestParameter uploadFileRequestParameter, String token) throws IOException {

        // 文件sTestsetFile：solr_etl_agent35.json是存有JSON字符串的文件
        //String filePath=System.getProperty("user.dir")+ File.separator+"image"+File.separator+"face.jpg";
        String filePath=System.getProperty("user.dir")+ File.separator+"image"+File.separator+uploadFileRequestParameter.getFileName();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(uploadFileUrl);
        uploadFile.setHeader("Authorization",token);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("filedirName", uploadFileRequestParameter.getFileDirName(), ContentType.TEXT_PLAIN);
        // 把文件加到HTTP的post请求中
        File f = new File(filePath);
        builder.addBinaryBody(
                "file",
                new FileInputStream(f),
                ContentType.MULTIPART_FORM_DATA,
                f.getName()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String sResponse= EntityUtils.toString(responseEntity, "UTF-8");
        System.out.println("Post 返回结果"+sResponse);
        JSONObject responsejsonObject= JSON.parseObject(sResponse);
        return  responsejsonObject;
    }

    public JSONObject uploadTwoImage(UploadFileRequestParameter uploadFileRequestParameter1,UploadFileRequestParameter uploadFileRequestParameter2, String token) throws IOException {

        String filePath1=System.getProperty("user.dir")+ File.separator+"image"+File.separator+uploadFileRequestParameter1.getFileName();
        String filePath2=System.getProperty("user.dir")+ File.separator+"image"+File.separator+uploadFileRequestParameter2.getFileName();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(uploadFileUrl);
        uploadFile.setHeader("Authorization",token);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("filedirName", uploadFileRequestParameter1.getFileDirName(), ContentType.TEXT_PLAIN);
        // 把文件加到HTTP的post请求中
        File f1 = new File(filePath1);
        File f2 = new File(filePath2);
        builder.addBinaryBody(
                "file",
                new FileInputStream(f1),
                ContentType.MULTIPART_FORM_DATA,
                f1.getName()
        );
        builder.addBinaryBody(
                "file",
                new FileInputStream(f2),
                ContentType.MULTIPART_FORM_DATA,
                f2.getName()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        HttpEntity responseEntity = response.getEntity();
        String sResponse= EntityUtils.toString(responseEntity, "UTF-8");
        System.out.println("Post 返回结果"+sResponse);
        JSONObject responsejsonObject= JSON.parseObject(sResponse);
        return  responsejsonObject;
    }





}
