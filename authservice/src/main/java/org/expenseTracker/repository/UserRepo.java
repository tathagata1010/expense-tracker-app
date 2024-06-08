package org.expenseTracker.repository;

import org.expenseTracker.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserInfo,String> {
   public UserInfo findByUserName(String username);
}
