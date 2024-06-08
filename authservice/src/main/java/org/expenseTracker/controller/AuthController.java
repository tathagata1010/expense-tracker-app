package org.expenseTracker.controller;

import lombok.AllArgsConstructor;
import org.expenseTracker.entities.RefreshToken;
import org.expenseTracker.model.UserInfoDto;
import org.expenseTracker.response.JwtResponseDTO;
import org.expenseTracker.service.JwtService;
import org.expenseTracker.service.RefreshTokenService;
import org.expenseTracker.service.UserDetailServiceImpl;
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
        try{
            Boolean isSignedUp=userDetailService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userInfoDto.getUserName());
            String jwtToken=jwtService.GenerateToken(userInfoDto.getUserName());
            return  new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).token(refreshToken.getRefreshToken()).build(),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
