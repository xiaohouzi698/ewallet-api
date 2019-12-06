package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 *  user_pan_card_case
 * @author hongtao 2019-11-07
 */
@Data
public class UserPanCardCase implements Serializable {
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
     * pancard正面照
     */
    private String panUrlFront;

    /**
     * pancard背面照
     */
    private String panUrlBack;

    /**
     * pancard正面照pdf
     */
    private String panPdfFront;

    /**
     * pancard背面照pdf
     */
    private String panPdfBack;

    /**
     * 持有人的全名
     */
    private String holdsFullName;

    /**
     * 持卡人的父亲姓名
     */
    private String fathersName;

    /**
     * 永久帐号
     */
    private String permanentNo;

    /**
     * 是否识别 0:未识别 1:识别
     */
    private Boolean identifyStatus;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 是否匹配 0:否 1:是
     */
    private Boolean matchStatus;

    /**
     * 匹配分数
     */
    private Integer matchScore;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserPanCardCase() {
    }

}