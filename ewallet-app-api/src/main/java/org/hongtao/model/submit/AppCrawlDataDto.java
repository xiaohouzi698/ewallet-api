/**
 * Copyright 2019 bejson.com
 */
package org.hongtao.model.submit;
import java.util.List;

/**
 * Auto-generated: 2019-11-08 14:48:1
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
import lombok.Data;

@Data
public class AppCrawlDataDto {

    private List<AppAddressBookDtoList> appAddressBookDtoList;
    private List<AppCallRecordDtoList> appCallRecordDtoList;
    private AppDeviceDto appDeviceDto;
    private List<AppMessagesDtoList> appMessagesDtoList;


}