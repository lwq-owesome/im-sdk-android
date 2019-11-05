package com.awesome.im.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author awesome
 * 认证对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImAuthenticateModel {

    private String userId;
    private String token;

}
