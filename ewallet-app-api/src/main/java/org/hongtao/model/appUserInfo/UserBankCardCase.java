package org.hongtao.model.appUserInfo;
import java.io.Serializable;
import lombok.Data;
import java.util.Date;


/**
 *  user_bank_card_case
 * @author 大狼狗 2019-12-05
 */
@Data
public class UserBankCardCase implements Serializable {

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
     * 银行卡帐户名
     */
    private String accountName;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * ifsc code
     */
    private String ifscCode;

    /**
     * 银行账号状态【0：待激活，1：已激活，2：激活失败】
     */
    private boolean status;

    /**
     * 校验返回持卡人姓名
     */
    private String verifyReturnName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 银行卡图片
     */
    private String bankUrl;

    public UserBankCardCase() {
    }

}