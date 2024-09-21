package com.daegurrr.daefree.service;

import com.daegurrr.daefree.authentication.JwtManager;
import com.daegurrr.daefree.dto.KakaoToken;
import com.daegurrr.daefree.dto.KakaoUserInfo;
import com.daegurrr.daefree.dto.LoginResponse;
import com.daegurrr.daefree.entity.Account;
import com.daegurrr.daefree.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final AccountRepository accountRepository;
    private final JwtManager jwtManager;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${oauth2.kakao.client-id}")
    String clientId;
    @Value("${oauth2.kakao.client-secret}")
    String clientSecret;
    @Value("${oauth2.kakao.redirect-uri}")
    String redirectUrl;
    @Value("${oauth2.kakao.token-uri}")
    String tokenUrl;
    @Value("${oauth2.kakao.userinfo-uri}")
    String userInfoUrl;

    public LoginResponse kakaoLogin(String code) {
        String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        KakaoToken kakaoToken = getKakaoToken(decode);
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoToken.getAccess_token());

        Account account = accountRepository.findById(kakaoUserInfo.getId())
                .orElseGet(() -> accountRepository.save(kakaoUserInfo.toEntity()));

        String jwt = jwtManager.issueToken(account.getId());
        return LoginResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .profileUrl(account.getProfileUrl())
                .accessToken(jwt)
                .build();
    }

    private KakaoToken getKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri",redirectUrl);
        params.add("grant_type", "authorization_code");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,headers);
        ResponseEntity<KakaoToken> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoToken.class
        );

        return response.getBody();
    }

    private KakaoUserInfo getKakaoUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                KakaoUserInfo.class
        );

        return response.getBody();
    }



}
