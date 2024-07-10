package org.expenseTracker.authService.controller;

import lombok.AllArgsConstructor;
import org.expenseTracker.authService.entities.RefreshToken;
import org.expenseTracker.authService.request.AuthRequestDTO;
import org.expenseTracker.authService.request.RefreshTokenDTO;
import org.expenseTracker.authService.response.JwtResponseDTO;
import org.expenseTracker.authService.service.JwtService;
import org.expenseTracker.authService.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(),authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(authRequestDTO.getUserName());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtService.GenerateToken(authRequestDTO.getUserName())).token(refreshToken.getRefreshToken()).build(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDTO responseToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        return  refreshTokenService.findByRefreshToken(refreshTokenDTO.getToken()).map(refreshTokenService::verifyExpiration).
                map(RefreshToken::getUserInfo).map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUserName());
                    return JwtResponseDTO.builder().accessToken(accessToken).token(refreshTokenDTO.getToken()).build();
                }).orElseThrow(()->new RuntimeException("Refresh Token not found!"));

    }
}
