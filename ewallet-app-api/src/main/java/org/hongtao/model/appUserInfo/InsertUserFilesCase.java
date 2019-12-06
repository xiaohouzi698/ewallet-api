package org.hongtao.model.appUserInfo;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;

/**
 *  user_info_files_case
 * @author hongtao 2019-11-08
 */
@Data
public class InsertUserFilesCase implements Serializable {
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
     * 银行流水url
     */
    private String bankStatementUrl;

    /**
     * 工作证明url
     */
    private String businessCardUrl;

    /**
     * 薪资流水url
     */
    private String salarySlipUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public InsertUserFilesCase() {
    }

}
