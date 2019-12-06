package org.hongtao.enums;

/**
 * @author yoga
 * @Description: LoginTypeEnum
 * @date 2019/10/1112:36
 */
public enum LoginTypeEnum {
    APP("APP"),
    BACK("BACK")
    ;

    private String type;

    LoginTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}