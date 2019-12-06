package org.hongtao.cases.app;


import org.hongtao.cases.appRegister.AppUserLoginTest;
import org.hongtao.cases.appRegister.PutUserPasswordTest;
import org.hongtao.cases.appRegister.RegisterVerifyCodeTest;
import org.hongtao.cases.appRegister.UserIsRegisteredTest;
import org.hongtao.cases.appUserInfo.*;
import org.hongtao.cases.submit.ApplySubmitTest;
import org.hongtao.model.InterfaceName;
import org.hongtao.utils.ConfigFile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static org.hongtao.config.TestConfig.*;

public class AppSubmitWallet {

    private static Logger logger = Logger.getLogger(String.valueOf(AppUserLoginTest.class));
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @BeforeTest(groups = "appRegister", description = "测试准备工作，获取 HttpClient 对象")
    public void beforeTest() {
        userIsRegisteredUrl = ConfigFile.getUrl(InterfaceName.USERISREGISTERED);
        appUserLoginUrl = ConfigFile.getUrl(InterfaceName.APPUSERLOGIN);
        registerVerifyCodeUrl = ConfigFile.getUrl(InterfaceName.REGISTERVERIFYCODE);
        putUserPasswordUrl = ConfigFile.getUrl(InterfaceName.PUTUSERPASSWORD);
        insertUserAadhaarCardUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERAADHAARCARD);
        insertUserBankCardUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERBANKCARD);
        insertUserContactUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERCONTACT);
        insertUserFilesUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERFILES);
        insertUserPanCardUrl = ConfigFile.getUrl(InterfaceName.INSERTUSERPANCARD);
        queryCertificationStatusUrl = ConfigFile.getUrl(InterfaceName.QUERYCERTIFICATIONSTATUS);
        uploadFileUrl = ConfigFile.getUrl(InterfaceName.UPLOADFILE);
        updateUserInfoFaceUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFOFACEURL);
        updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        updateUserWorkUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERWORK);
        applySubmitUrl = ConfigFile.getUrl(InterfaceName.APPLYSUBMIT);

    }

    @Test(description = "多线程")
    public void appSubmitWallet() throws  InterruptedException {

        registerThread.start();
        registerThread.join();

        submitAppUserInfoThread1.start();
        submitAppUserInfoThread2.start();
        submitAppUserInfoThread1.join();
        submitAppUserInfoThread2.join();


        applySubmitThread.start();
        applySubmitThread.join();
    }

    @Test(description = "多线程")
    public void appSubmitWalletThread() throws  InterruptedException {

        executorService.submit(registerThread);
        executorService.submit(submitAppUserInfoThread1);
        executorService.submit(submitAppUserInfoThread2);
        executorService.submit(applySubmitThread);

    }

    static Thread registerThread = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                UserIsRegisteredTest userIsRegisteredTest = new UserIsRegisteredTest();
                userIsRegisteredTest.registerReturnToken();
                RegisterVerifyCodeTest registerVerifyCodeTest = new RegisterVerifyCodeTest();
                registerVerifyCodeTest.registerReturnToken();
                PutUserPasswordTest putUserPasswordTest = new PutUserPasswordTest();
                putUserPasswordTest.registerAndPutUserPassword();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    });


    static Thread submitAppUserInfoThread1 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                AppUserLoginTest appUserLoginTest = new AppUserLoginTest();
                appUserLoginTest.truePassword();
                UploadFileTest uploadFileTest = new UploadFileTest();
                uploadFileTest.uploadFaceImage();
                UpdateUserInfoFaceUrlTest updateUserInfoFaceUrlTest = new UpdateUserInfoFaceUrlTest();
                updateUserInfoFaceUrlTest.updateUserInfoFaceUrl();
                uploadFileTest.uploadAadhaarImage();
                InsertUserAadhaarCardTest insertUserAadhaarCardTest = new InsertUserAadhaarCardTest();
                insertUserAadhaarCardTest.insertUserAadhaarCard();
                uploadFileTest.uploadPancardImage();
                InsertUserPanCardTest insertUserPanCardTest = new InsertUserPanCardTest();
                insertUserPanCardTest.insertUserPanCard();
                uploadFileTest.uploadBankStatementImage();
                uploadFileTest.uploadWorkCredentialImage();
                uploadFileTest.uploadSalarySlipsImage();
                InsertUserFilesTest insertUserFilesTest = new InsertUserFilesTest();
                insertUserFilesTest.insertUserFiles();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    });

    static Thread submitAppUserInfoThread2 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                UploadFileTest uploadFileTest = new UploadFileTest();
                UpdateUserInfoTest updateUserInfoTest = new UpdateUserInfoTest();
                updateUserInfoTest.updateUserInfo();
                UpdateUserWorkTest updateUserWorkTest = new UpdateUserWorkTest();
                updateUserWorkTest.updateUserWork();
                InsertUserContactTest insertUserContactTest = new InsertUserContactTest();
                insertUserContactTest.insertUserContact();
                uploadFileTest.uploadBankcardImage();
                InsertUserBankCardTest insertUserBankCardTest = new InsertUserBankCardTest();
                insertUserBankCardTest.insertUserBankCard();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    });

    static Thread applySubmitThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                ApplySubmitTest applySubmitTest = new ApplySubmitTest();
                applySubmitTest.applySubmit();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
    });

}
