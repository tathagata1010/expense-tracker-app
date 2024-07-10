package org.expenseTracker.userService.service;

import lombok.RequiredArgsConstructor;
import org.expenseTracker.userService.entities.UserInfo;
import org.expenseTracker.userService.entities.UserInfoDTO;
import org.expenseTracker.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserInfoDTO createOrUpdateUser(UserInfoDTO userInfoDTO){
        Function<UserInfo,UserInfo> updateUser= user->{
            user.setUserId(userInfoDTO.getUserId());
            user.setEmail(user.getEmail());
            user.setFirstName(userInfoDTO.getFirstName());
            user.setLastName(userInfoDTO.getLastName());
            user.setPhoneNumber(userInfoDTO.getPhoneNumber());
            return userRepository.save(user);
        };

        Supplier<UserInfo> createUser = ()->{
          return userRepository.save(userInfoDTO.transformToUserInfo());
        };

        UserInfo userInfo=userRepository.findByUserId(userInfoDTO.getUserId()).map(updateUser).orElseGet(createUser);

        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }

    public UserInfoDTO getUser(UserInfoDTO userInfoDTO) throws Exception{
        Optional<UserInfo> infoDTO=userRepository.findByUserId(userInfoDTO.getUserId());
        if (infoDTO.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo=infoDTO.get();
        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }
}
