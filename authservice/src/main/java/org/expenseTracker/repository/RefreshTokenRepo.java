package org.expenseTracker.repository;

import org.expenseTracker.entities.RefreshToken;
import org.expenseTracker.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken,Long> {

     Optional<RefreshToken> findByRefreshToken(String token);

//     UserInfo findByUserNameAndPassword(String userName, String password)
}
