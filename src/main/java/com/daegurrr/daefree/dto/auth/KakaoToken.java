package com.daegurrr.daefree.dto.auth;

import lombok.Getter;

@Getter
public class KakaoToken {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;
    private String scope;
}
