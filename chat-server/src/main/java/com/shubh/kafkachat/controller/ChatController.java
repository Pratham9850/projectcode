package com.shubh.kafkachat.controller;


import com.shubh.kafkachat.constants.KafkaConstants;
import com.shubh.kafkachat.model.Message;
import com.shubh.kafkachat.model.MessageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


@RestController
public class ChatController {
	
	@Autowired
    SimpMessagingTemplate template;
	
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    
    @PostMapping(value = "/api/send", consumes = { "multipart/form-data" })
    public void sendMessage(
            @RequestPart("sender") String sender,
            @RequestPart("type") MessageType type,
            @RequestPart(value = "content", required = false) String content,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Message message = new Message();
        message.setSender(sender);
        message.setType(type);
        message.setTimestamp(LocalDateTime.now().toString());

        if (type == MessageType.TEXT && content != null) {
            message.setContent(content);
        } else if (type == MessageType.FILE && file != null && !file.isEmpty()) {
            try {
                message.setFileContent(file.getBytes());
                message.setFileName(file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Invalid message type or missing content/file.");
        }

        try {
            // Sending the message (text or file) to Kafka topic
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    
    
    private Map<String, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();

    @MessageMapping("/user/connect")
    public void connect(Principal principal, WebSocketSession session) {
        onlineUsers.put(principal.getName(), session);
        sendOnlineUsersUpdate();
    }

    @MessageMapping("/user/disconnect")
    public void disconnect(Principal principal) {
        onlineUsers.remove(principal.getName());
        sendOnlineUsersUpdate();
    }

    private void sendOnlineUsersUpdate() {
        template.convertAndSend("/topic/onlineUsers", onlineUsers.keySet());
    }

//    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
//    public void sendMessage(@RequestBody Message message) {
//        message.setTimestamp(LocalDateTime.now().toString());
//        try {
//            //Sending the message to kafka topic queue
//            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @GetMapping(value ="/message",consumes = "application/json", produces = "application/json")
//   	public ArrayList<Message> getEmployees(){
//    		
//    	}
//    }

    //    -------------- WebSocket API ----------------
   /* @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }*/

}
