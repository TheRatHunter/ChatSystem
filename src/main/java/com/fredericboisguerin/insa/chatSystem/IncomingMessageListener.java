package com.fredericboisguerin.insa.chatSystem;

public interface IncomingMessageListener {
    void onNewIncomingMessage(String message);
}