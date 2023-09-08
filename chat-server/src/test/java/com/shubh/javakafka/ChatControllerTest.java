package com.shubh.javakafka;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.shubh.kafkachat.constants.KafkaConstants;
import com.shubh.kafkachat.controller.ChatController;
import com.shubh.kafkachat.model.Message;
import com.shubh.kafkachat.model.MessageType;


@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class ChatControllerTest {

	
	@Mock
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private MockMvc mockMvc;
    
    private ChatController chatController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        chatController = new ChatController(simpMessagingTemplate, kafkaTemplate);
    }
    
    @Test
    void testSendMessageText1() throws Exception {
        String sender = "user1";
        MessageType type = MessageType.TEXT;
        String content = "Hello, world!";
        MultipartFile file = null;

        // Perform a request to send a message
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/send")
                .param("sender", sender)
                .param("type", type.toString())
                .param("content", content)
                .param("file"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the message is sent to Kafka
//        Message message = KafkaTestUtils.getSingleRecord(consumer, KafkaConstants.KAFKA_TOPIC).value();
//        assertEquals(sender, message.getSender());
//        assertEquals(type, message.getType());
//        assertEquals(content, message.getContent());
    }
//    @Test
//    void testSendMessageText() throws InterruptedException, ExecutionException {
//        Message message = new Message("user1", "Hello, world!");
//
//        chatController.sendMessage("user1", MessageType.TEXT, "Hello, world!", null);
//
//        verify(kafkaTemplate, times(1)).send(eq(KafkaConstants.KAFKA_TOPIC), eq(message));
//    }

}
