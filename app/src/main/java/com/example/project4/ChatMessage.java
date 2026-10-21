package com.example.project4;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String text;
    private boolean isSentByMe;

    public ChatMessage(String text, boolean isSentByMe) {
        this.text = text;
        this.isSentByMe = isSentByMe;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByMe() {
        return isSentByMe;
    }
}
