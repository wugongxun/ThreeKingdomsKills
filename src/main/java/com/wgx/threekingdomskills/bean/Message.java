package com.wgx.threekingdomskills.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:wgx
 * version:1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    //状态码200正常，500错误
    private Integer statusCode;
    private String message;
    private Object data;
}
