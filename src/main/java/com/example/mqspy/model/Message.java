package com.example.mqspy.model;

import java.time.LocalDateTime;

public class Message {

    private LocalDateTime putTimeStamp;
    private String sourceQueue;
    private String sourceQueueManager;
    private String messageId;
    private String correlationId;
    private String messageHeader;
    private String messagePayload;

    public LocalDateTime getPutTimeStamp() {
        return putTimeStamp;
    }

    public void setPutTimeStamp(LocalDateTime putTimeStamp) {
        this.putTimeStamp = putTimeStamp;
    }

    public String getSourceQueue() {
        return sourceQueue;
    }

    public void setSourceQueue(String sourceQueue) {
        this.sourceQueue = sourceQueue;
    }

    public String getSourceQueueManager() {
        return sourceQueueManager;
    }

    public void setSourceQueueManager(String sourceQueueManager) {
        this.sourceQueueManager = sourceQueueManager;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(String messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }
}
