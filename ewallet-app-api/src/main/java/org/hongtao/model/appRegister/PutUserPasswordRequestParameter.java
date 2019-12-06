package org.hongtao.model.appRegister;
import lombok.Data;

@Data
public class PutUserPasswordRequestParameter {
    private int id;
    private String mobile;
    private String tmpToken;
    private String encryptedString;

}
