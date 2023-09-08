package com.shubh.javakafka;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.shubh.kafkachat.model.Message;
import com.shubh.kafkachat.model.MessageType;

class MessageTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
    void testCreateTextMessage() {
        String sender = "user1";
        String content = "Hello, world!";
        Message message = new Message(sender, content);

        assertEquals(sender, message.getSender());
        assertEquals(content, message.getContent());
        assertNull(message.getFileContent());
        assertNull(message.getFileName());
        //assertNotNull(message.getTimestamp());
       // assertEquals(MessageType.TEXT, message.getType());
    }
	
	@Test
    void testCreateFileMessage() {
        String sender = "user1";
        byte[] fileContent = "File content".getBytes();
        String fileName = "file.txt";
        Message message = new Message(sender, null, LocalDateTime.now().toString(), fileContent, fileName);

        assertEquals(sender, message.getSender());
        assertNull(message.getContent());
        assertArrayEquals(fileContent, message.getFileContent());
        assertEquals(fileName, message.getFileName());
        assertNotNull(message.getTimestamp());
        //assertEquals(MessageType.FILE, message.getType());
    }

	
//	@Test
//    void testCreateFileMessage() {
//        byte[] fileContent = "File content".getBytes();
//        Message message = new Message("user1", "file.txt", LocalDateTime.now().toString(), MessageType.FILE, fileContent, "file.txt");
//
//        assertEquals("user1", message.getSender());
//        assertNull(message.getContent());
//        assertArrayEquals("File content".getBytes(), message.getFileContent());
//        assertEquals("file.txt", message.getFileName());
//        assertNotNull(message.getTimestamp());
//        assertEquals(MessageType.FILE, message.getType());
    }


