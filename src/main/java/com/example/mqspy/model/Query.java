package com.example.mqspy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "queries")
public class Query {

    @Id
    @GeneratedValue
    private Long Id;
    private String queryId;
    private String environment;
    private List<String> queueNames;
    private List<String> searchParameters;
    private LocalDateTime timeStampFrom;
    private LocalDateTime timeStampTo;
    private Boolean searchParametersAndRelation;
    private Boolean getOnlyQueueDepth;
    private LocalDateTime queryStartTime;
    private LocalDateTime queryEndTime;
    private Duration queryDuration;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(List<String> queueNames) {
        this.queueNames = queueNames;
    }

    public List<String> getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(List<String> searchParameters) {
        this.searchParameters = searchParameters;
    }

    public LocalDateTime getTimeStampFrom() {
        return timeStampFrom;
    }

    public void setTimeStampFrom(LocalDateTime timeStampFrom) {
        this.timeStampFrom = timeStampFrom;
    }

    public LocalDateTime getTimeStampTo() {
        return timeStampTo;
    }

    public void setTimeStampTo(LocalDateTime timeStampTo) {
        this.timeStampTo = timeStampTo;
    }

    public Boolean getSearchParametersAndRelation() {
        return searchParametersAndRelation;
    }

    public void setSearchParametersAndRelation(Boolean searchParametersAndRelation) {
        this.searchParametersAndRelation = searchParametersAndRelation;
    }

    public Boolean getGetOnlyQueueDepth() {
        return getOnlyQueueDepth;
    }

    public void setGetOnlyQueueDepth(Boolean getOnlyQueueDepth) {
        this.getOnlyQueueDepth = getOnlyQueueDepth;
    }

    public LocalDateTime getQueryStartTime() {
        return queryStartTime;
    }

    public void setQueryStartTime(LocalDateTime queryStartTime) {
        this.queryStartTime = queryStartTime;
    }

    public LocalDateTime getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(LocalDateTime queryEndTime) {
        this.queryEndTime = queryEndTime;
    }

    public Duration getQueryDuration() {
        return queryDuration;
    }

    public void setQueryDuration(Duration queryDuration) {
        this.queryDuration = queryDuration;
    }
}
