package org.hongtao.model;
import lombok.Data;

@Data
public class AddAadhaarInfoCase {
    private String aadhaarAccount;
    private String aadhaarBirthday;
    private String aadhaarGender;
    private String aadhaarImgUrl;
    private String aadhaarName;
    private String aadhaarPdfBack;
    private String aadhaarPdfFront;
    private String aadhaarUrlBack;
    private String aadhaarUrlFront;
    private String id;
    private String userId;
    private String expected;
}

