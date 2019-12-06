package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 *  user_address_case
 * @author hongtao 2019-11-08
 */
@Data
public class UserAddressCase implements Serializable {
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
     * 单位电话
     */
    private String companyPhone;

    /**
     * 地址详情
     */
    private String addressDetail;

    /**
     * 州（邦）
     */
    private String state;

    /**
     * 区
     */
    private String district;

    /**
     * 县
     */
    private String county;

    /**
     * 村镇
     */
    private String town;

    /**
     * 地址类型；0>家庭地址，1>单位地址，2>学校地址；
     */
    private Boolean addressType;

    /**
     * 状态 0:未启用 1:已启用
     */
    private Boolean addressStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserAddressCase() {
    }

}