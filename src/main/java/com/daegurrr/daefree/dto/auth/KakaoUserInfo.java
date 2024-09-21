package com.daegurrr.daefree.dto.auth;

import com.daegurrr.daefree.entity.Account;
import lombok.Getter;


@Getter
public class KakaoUserInfo {
    private Long id;
    private String connected_at;
    private Properties properties;

    @Getter
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    public Account toEntity() {
        return Account.builder()
                .id(id)
                .name(properties.nickname)
                .profileUrl(properties.profile_image)
                .build();
    }

}
