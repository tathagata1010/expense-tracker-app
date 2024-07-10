package org.expenseTracker.userService.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.expenseTracker.userService.entities.UserInfoDTO;

import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public UserInfoDTO deserialize(String var1, byte[] var2){
        ObjectMapper objectMapper=new ObjectMapper();
        UserInfoDTO user=null;
        try{
            String jsonString = new String(var2);
            System.out.println("Deserializing JSON: " + jsonString);  // Log the input JSON
            user=objectMapper.readValue(var2, UserInfoDTO.class);
            System.out.println(user);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() {}
}
