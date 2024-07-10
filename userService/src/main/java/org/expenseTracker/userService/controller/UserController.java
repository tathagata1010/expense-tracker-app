package org.expenseTracker.userService.controller;

import lombok.RequiredArgsConstructor;
import org.expenseTracker.userService.entities.UserInfoDTO;
import org.expenseTracker.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user/v1/CreateUpdate")
    public ResponseEntity<UserInfoDTO> createUpdateUser(@RequestBody UserInfoDTO userInfoDTO){
        try{
            UserInfoDTO infoDTO= userService.createOrUpdateUser(userInfoDTO);
            return new ResponseEntity<>(infoDTO, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/v1/getUser")
    public ResponseEntity<UserInfoDTO> getUser(@RequestBody UserInfoDTO userInfoDTO){
        try{
            UserInfoDTO infoDTO=userService.getUser(userInfoDTO);
            return new ResponseEntity<>(infoDTO,HttpStatus.FOUND);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
