package org.expenseTracker.authService.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.expenseTracker.authService.eventProducer.UserInfoEvent;
import org.expenseTracker.authService.model.UserInfoDto;

import java.util.Map;

public class UserDetailsSerializer implements Serializer<UserInfoEvent> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, UserInfoEvent userInfoEvent) {
        byte[] retVal=null;
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            retVal= objectMapper.writeValueAsString(userInfoEvent).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return retVal;
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
