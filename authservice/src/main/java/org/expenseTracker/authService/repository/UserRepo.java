package org.expenseTracker.authService.repository;

import org.expenseTracker.authService.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserInfo,String> {
   public UserInfo findByUserName(String username);
}
