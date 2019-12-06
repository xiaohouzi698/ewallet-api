package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  user_salary_case
 * @author hongtao 2019-11-08
 */
@Data
public class UserSalaryCase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 薪资
     */
    private BigDecimal salary;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserSalaryCase() {
    }

}