package org.hongtao.model.appRegister;
import lombok.Data;

@Data
public class RegisterVerifyCodeRequestParameter {
    private int id;
    private String mobile;
    private String code="666666";
}
