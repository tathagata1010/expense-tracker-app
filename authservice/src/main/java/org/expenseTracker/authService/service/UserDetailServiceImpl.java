package org.expenseTracker.authService.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.expenseTracker.authService.entities.UserInfo;
import org.expenseTracker.authService.eventProducer.UserInfoEvent;
import org.expenseTracker.authService.repository.UserRepo;
import org.expenseTracker.authService.eventProducer.UserInfoProducer;
import org.expenseTracker.authService.model.UserInfoDto;
import org.expenseTracker.authService.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    ValidationUtils validationUtils;

    @Autowired
    UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepo.findByUsername(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(userInfo);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfo userInfoData) {
        return userRepo.findByUsername(userInfoData.getUsername());
    }

    public Boolean signupUser(UserInfoDto userInfoDto) {
        try {
            validationUtils.validateUser(userInfoDto.getEmail(), userInfoDto.getPassword());
            userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
            if (Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))) {
                return false;
            }
            String userId = UUID.randomUUID().toString();
            userRepo.save(UserInfo.builder()
                    .userId(userId) 
                    .username(userInfoDto.getUsername())
                    .password(userInfoDto.getPassword())
                    .roles(new HashSet<>())
                    .build());
            userInfoProducer.sendEventToKafka(userInfoEventToPublish(userInfoDto,userId));
            return true;
        } catch (IllegalArgumentException e) {
            // Validation failed, return false
            return false;
        }
    }

    private UserInfoEvent userInfoEventToPublish(UserInfoDto userInfoDto, String userId){
        return UserInfoEvent.builder()
                .userId(userId)
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber()).build();

    }

}
