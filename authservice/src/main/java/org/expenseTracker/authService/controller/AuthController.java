package org.expenseTracker.authService.controller;

import lombok.AllArgsConstructor;
import org.expenseTracker.authService.entities.RefreshToken;
import org.expenseTracker.authService.service.UserDetailServiceImpl;
import org.expenseTracker.authService.model.UserInfoDto;
import org.expenseTracker.authService.response.JwtResponseDTO;
import org.expenseTracker.authService.service.JwtService;
import org.expenseTracker.authService.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    @Autowired
    JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
        System.out.println(userInfoDto+" User info dto");
        try{
            Boolean isSignedUp=userDetailService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken=jwtService.GenerateToken(userInfoDto.getUsername());
            return  new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).token(refreshToken.getRefreshToken()).build(),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
