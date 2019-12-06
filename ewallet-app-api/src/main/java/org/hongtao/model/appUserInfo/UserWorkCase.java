package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 *  user_work_case
 * @author hongtao 2019-11-08
 */
@Data
public class UserWorkCase implements Serializable {
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
     * 职业
     */
    private String profession;

    /**
     * 职位
     */
    private String position;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 工作年限
     */
    private String workYear;

    /**
     * 工作证明图片url
     */
    private String workCard;

    /**
     * 银行流水图片
     */
    private String bankStatementUrl;

    /**
     * 银行流水图片pdf
     */
    private String bankStatementPdf;

    /**
     * 发薪日
     */
    private Integer payday;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学校
     */
    private String school;

    /**
     * 教育程度
     */
    private String educationDegree;

    /**
     * 公司电话/座机
     */
    private Long phone;

    public UserWorkCase() {
    }

}