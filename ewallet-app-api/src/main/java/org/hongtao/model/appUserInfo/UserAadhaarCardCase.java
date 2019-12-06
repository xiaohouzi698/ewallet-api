package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 *  user_aadhaar_card_case
 * @author hongtao 2019-11-07
 */
@Data
public class UserAadhaarCardCase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * aadhaar 正面照片url
     */
    private String aadhaarUrlFront;

    /**
     * aadhaar 反面照片url
     */
    private String aadhaarUrlBack;

    /**
     * aadhaar 正面照片pdf
     */
    private String aadhaarPdfFront;

    /**
     * aadhaar 反面照片pdf
     */
    private String aadhaarPdfBack;

    /**
     * aadhaar账号
     */
    private String aadhaarAccount;

    /**
     * aadhaar姓名
     */
    private String aadhaarName;

    /**
     * aadhaar生日
     */
    private String aadhaarBirthday;

    /**
     * aadhaar性别 ：0>男；1>女；
     */
    private Boolean aadhaarGender;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否识别：0>未识别，1>已识别
     */
    private Integer identifyStatus;

    public UserAadhaarCardCase() {
    }

}