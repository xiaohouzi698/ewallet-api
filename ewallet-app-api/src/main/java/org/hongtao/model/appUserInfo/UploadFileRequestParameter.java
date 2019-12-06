package org.hongtao.model.appUserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 *  upload_file_case
 * @author 大狼狗 2019-12-05
 */
@Data
public class UploadFileRequestParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * file_name
     */
    private String fileName;

    /**
     * file_dir_name
     */
    private String fileDirName;

    public UploadFileRequestParameter() {
    }

}