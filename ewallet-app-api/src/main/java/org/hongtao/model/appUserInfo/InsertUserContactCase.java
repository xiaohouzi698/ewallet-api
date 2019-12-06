package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 *  user_contact_case
 * @author hongtao 2019-11-08
 */
@Data
public class InsertUserContactCase implements Serializable {
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
     * 名字
     */
    private String name;

    /**
     * 关系>0：parent，1：brother，2：sister，3：child，4：colleague，5：friend，6：classmate
     */
    private Boolean relation;

    /**
     * 手机号码
     */
    private Long phone;

    /**
     * 0未启用 1：启用
     */
    private Boolean enabledState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 联系人排名：0：第一联系人，1：第二联系人
     */
    private Boolean ranking;

    public InsertUserContactCase() {
    }

}