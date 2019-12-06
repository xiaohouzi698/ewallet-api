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




        testUrl = address + uri;
        return testUrl;
    }
}
