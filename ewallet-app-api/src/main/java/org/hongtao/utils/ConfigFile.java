package org.hongtao.utils;

import org.hongtao.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {
    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUrl(InterfaceName name){
        String address = bundle.getString("test.url");
        String uri = "";
        // 最终的测试地址
        String testUrl;

        if(name == InterfaceName.USERISREGISTERED){
            uri = bundle.getString("userIsRegistered.uri");
        }

        if(name == InterfaceName.GETLATESTVERSION){
            uri = bundle.getString("getLatestVersion.uri");
        }

        if(name == InterfaceName.APPUSERLOGIN){
            uri = bundle.getString("appUserLogin.uri");
        }
        if(name == InterfaceName.PUTUSERPASSWORD){
            uri = bundle.getString("putUserPassword.uri");
        }
        if(name == InterfaceName.REGISTERVERIFYCODE){
            uri = bundle.getString("registerVerifyCode.uri");
        }

        if(name == InterfaceName.GETAPPINDEXPRODUCTINFO){
            uri = bundle.getString("getAppIndexProductInfo.uri");
        }

        if(name == InterfaceName.GETAPPINDEXRESULT){
            uri = bundle.getString("getAppIndexResult.uri");
        }


        if(name == InterfaceName.UPLOADFILE){
            uri = bundle.getString("uploadFile.uri");
        }


        if(name == InterfaceName.UPDATEUSERINFOFACEURL){
            uri = bundle.getString("updateUserInfoFace.uri");
        }


        if(name == InterfaceName.INSERTUSERAADHAARCARD){
            uri = bundle.getString("insertUserAadhaarCard.uri");
        }


        if(name == InterfaceName.QUERYCERTIFICATIONSTATUS){
            uri = bundle.getString("queryCertificationStatus.uri");
        }

        if(name == InterfaceName.INSERTUSERPANCARD){
            uri = bundle.getString("insertUserPanCard.uri");
        }


        if(name == InterfaceName.INSERTUSERFILES){
            uri = bundle.getString("insertUserFiles.uri");
        }

        if(name == InterfaceName.GETUPDATEUSERINFO){
            uri = bundle.getString("getUpdateUserInfo.uri");
        }

        if(name == InterfaceName.GETUPDATEUSERSTUDENT){
            uri = bundle.getString("getUpdateUserStudent.uri");
        }

        if(name == InterfaceName.GETUPDATEUSERWORK){
            uri = bundle.getString("getUserBankCard.uri");
        }

        if(name == InterfaceName.GETUSERBANKCARD){
            uri = bundle.getString("getUserContact.uri");
        }

        if(name == InterfaceName.GETUSERCONTACT){
            uri = bundle.getString("getUpdateUserInfo.uri");
        }

        if(name == InterfaceName.INSERTUSERCONTACT){
            uri = bundle.getString("insertUserContact.uri");
        }

        if(name == InterfaceName.INSERTUSERBANKCARD){
            uri = bundle.getString("insertUserBankcard.uri");
        }

        if(name == InterfaceName.APPLYSUBMIT){
            uri = bundle.getString("applySubmit.uri");
        }

        if(name == InterfaceName.UPDATEUSERINFO){
            uri = bundle.getString("updateUserInfo.uri");
        }

        if(name == InterfaceName.UPDATEUSERWORK){
            uri = bundle.getString("updateUserWork.uri");
        }




        testUrl = address + uri;
        return testUrl;
    }
}
