package org.expenseTracker.authService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.expenseTracker.authService.entities.UserInfo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class UserInfoDto extends UserInfo {

    private String email;
    @JsonProperty("username")
    private String userName;
    private String password;
    private Long phoneNumber;

}
