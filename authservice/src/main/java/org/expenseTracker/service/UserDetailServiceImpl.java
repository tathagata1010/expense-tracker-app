package org.expenseTracker.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.expenseTracker.entities.UserInfo;
import org.expenseTracker.model.UserInfoDto;
import org.expenseTracker.repository.UserRepo;
import org.expenseTracker.utils.ValidationUtils;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepo.findByUserName(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(userInfo);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfo userInfoData) {
        return userRepo.findByUserName(userInfoData.getUserName());
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
                    .userName(userInfoDto.getUserName())
                    .password(userInfoDto.getPassword())
                    .roles(new HashSet<>())
                    .build());
            return true;
        } catch (IllegalArgumentException e) {
            // Validation failed, return false
            return false;
        }
    }

}
