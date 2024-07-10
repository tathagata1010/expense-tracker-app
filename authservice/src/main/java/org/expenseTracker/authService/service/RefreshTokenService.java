package org.expenseTracker.authService.service;

import org.expenseTracker.authService.entities.RefreshToken;
import org.expenseTracker.authService.entities.UserInfo;
import org.expenseTracker.authService.repository.RefreshTokenRepo;
import org.expenseTracker.authService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepo userRepo;

    public RefreshToken createRefreshToken(String userName){
        UserInfo userInfoExtracted=userRepo.findByUserName(userName);

        RefreshToken refreshToken=RefreshToken.builder().
                userInfo(userInfoExtracted).refreshToken(UUID.randomUUID().toString()).
                expiryDate(Instant.now().plusMillis(600000)).build();
        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException(refreshToken.getRefreshToken()+ "Refresh Token is expired. Please make a new login");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByRefreshToken(String token){
        return refreshTokenRepo.findByRefreshToken(token);
    }
}
