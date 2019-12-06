package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 *  user_info_case
 * @author hongtao 2019-11-08
 */
@Data
public class UserInfoCase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名
     */
    private String userName;

    /**
     * 性别 ：0>男；1>女；
     */
    private Boolean gender;

    /**
     * 手机号
     */
    private Long mobile;

    /**
     * email
     */
    private String email;

    /**
     * 客户状态：0>新建；1>激活；2>冻结；3>删除（不做显示，可暂时不做）
     */
    private Boolean custStatus;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 婚姻状况：0>unmarried，1>married but childless，2>married with children，3>divorced/widowed
     */
    private String marriageStatus;

    /**
     * 是否是学生 0>不是；1>是；
     */
    private Boolean studentStatus;

    /**
     * 渠道来源
     */
    private String channel;

    /**
     * 宗教
     */
    private String religion;

    /**
     * 借款用途
     */
    private String loanPurpose;

    /**
     * 邮编
     */
    private Integer pinCode;

    /**
     * 匹配分数
     */
    private Integer matchScore;

    /**
     * 续贷标识 0：老客 1：新客
     */
    private Boolean renewalStatus;

    /**
     * 信用账户
     */
    private String creditAccount;

    /**
     * 头像url
     */
    private String profileUrl;

    /**
     * 人脸照片url
     */
    private String photoUrl;

    /**
     * facebook账号
     */
    private String facebookAccount;

    /**
     * whatsapp账号
     */
    private String whatsappAccount;

    /**
     * skype账号
     */
    private String skypeAccount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserInfoCase() {
    }

}
