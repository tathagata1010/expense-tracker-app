package org.expenseTracker.authService.repository;

import org.expenseTracker.authService.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken,Long> {

     Optional<RefreshToken> findByRefreshToken(String token);

//     UserInfo findByUserNameAndPassword(String userName, String password)
}
