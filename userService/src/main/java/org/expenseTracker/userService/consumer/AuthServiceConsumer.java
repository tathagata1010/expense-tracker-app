package org.expenseTracker.userService.consumer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.expenseTracker.userService.entities.UserInfoDTO;
import org.expenseTracker.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {

    @Autowired
    public UserService userService;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}",groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void listen(UserInfoDTO eventData){
        try{
            userService.createOrUpdateUser(eventData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
