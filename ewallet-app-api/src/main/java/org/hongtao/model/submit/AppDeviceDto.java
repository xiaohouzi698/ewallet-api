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
public class AppDeviceDto {

    private String altitude;
    private List<String> applicationNames;
    private String battery;
    private String gpsAddress;
    private String imiId;
    private String ip;
    private Double longitude;
    private String manufacturer;
    private String model;
    private int networkType;
    private int phoneModel;

}