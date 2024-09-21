package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.LoginResponse;
import com.daegurrr.daefree.service.OAuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "회원 인증(가입/로그인)관련 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final OAuthService oAuthService;

    @Hidden
    @GetMapping("oauth/redirect")
    public ResponseEntity<LoginResponse> googleOAuth(@RequestParam(name="code")String code){
        return ResponseEntity.ok().body(oAuthService.kakaoLogin(code));
    }

    @PostMapping("api/auth")
    @Operation(summary="OAuth 회원가입/로그인 API")
    public ResponseEntity<LoginResponse> googleOAuthTest(@RequestBody String code){
        return ResponseEntity.ok().body(oAuthService.kakaoLogin(code));
    }
}
