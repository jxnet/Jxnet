package com.jxpcap.util;

public class Message {
    
    private String message;
    
    public Message(String message) {
        setMessage(message);
    }
    
    public String getMessage() {
        return message;
    }
    
    private void setMessage(String message) {
        this.message = message;
    }
}
