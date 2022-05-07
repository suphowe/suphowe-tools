package com.soft.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 返回消息类
 * @author suphowe
 */
@Data
public class ResponseMsg {

    @JsonProperty("Code")
    String Code;

    @JsonProperty("MsgEn")
    Object MsgEn;

    @JsonProperty("MsgCn")
    Object MsgCn;

    @JsonProperty("Data")
    Object Data;

}
