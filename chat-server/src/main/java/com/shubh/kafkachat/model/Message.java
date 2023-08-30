package com.shubh.kafkachat.model;

import java.util.Arrays;

public class Message {
    private String sender;
    private String content;
    private String timestamp;
    private MessageType type;
    private byte[] fileContent; // for files
    private String fileName; // original file name

    
    
    public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public Message(String sender, String content, String timestamp, byte[] fileContent, String fileName) {
		super();
		this.sender = sender;
		this.content = content;
		this.timestamp = timestamp;
		this.fileContent = fileContent;
		this.fileName = fileName;
	}

	public Message() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", content=" + content + ", timestamp=" + timestamp + ", fileContent="
				+ Arrays.toString(fileContent) + ", fileName=" + fileName + "]";
	}

	public void setType(MessageType type2) {
		// TODO Auto-generated method stub
		
	}
    
    

//    @Override
//    public String toString() {
//        return "Message{" +
//                "sender='" + sender + '\'' +
//                ", content='" + content + '\'' +
//                ", timestamp='" + timestamp + '\'' +
//                '}';
//    }
}
