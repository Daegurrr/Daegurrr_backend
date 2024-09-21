package com.daegurrr.daefree.controller;

import com.daegurrr.daefree.dto.auth.LoginRequest;
import com.daegurrr.daefree.dto.auth.LoginResponse;
import com.daegurrr.daefree.service.OAuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Hidden
    @GetMapping("api/auth/test")
    @Operation(summary="인가 테스트용 API (안씀!)")
    public ResponseEntity<String> authTest(Authentication authentication){
        return ResponseEntity.ok().body(authentication.getName());
    }

    @PostMapping("api/auth")
    @Operation(summary="OAuth 회원가입/로그인 API")
    public ResponseEntity<LoginResponse> googleOAuthTest(@RequestBody LoginRequest request){
        return ResponseEntity.ok().body(oAuthService.kakaoLogin(request.getCode()));
    }
}
