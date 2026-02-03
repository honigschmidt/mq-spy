package com.example.mqspy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QueryStats {

    private String queryId;
    private String environment;
    private List<String> searchParameters;
    private LocalDateTime timeStampFrom;
    private LocalDateTime timeStampTo;
    private Boolean searchParametersAndRelation;
    private Boolean getOnlyQueueDepth;
    private LocalDateTime queryStartTime;
    private LocalDateTime queryEndTime;
    private List<QueryStatsEntry> queryStatsEntries = new ArrayList<>();

    public QueryStats(String queryId, String environment, List<String> searchParameters, LocalDateTime timeStampFrom, LocalDateTime timeStampTo, Boolean searchParametersAndRelation, Boolean getOnlyQueueDepth, LocalDateTime queryStartTime, LocalDateTime queryEndTime, List<QueryStatsEntry> queryStatsEntries) {
        this.queryId = queryId;
        this.environment = environment;
        this.searchParameters = searchParameters;
        this.timeStampFrom = timeStampFrom;
        this.timeStampTo = timeStampTo;
        this.searchParametersAndRelation = searchParametersAndRelation;
        this.getOnlyQueueDepth = getOnlyQueueDepth;
        this.queryStartTime = queryStartTime;
        this.queryEndTime = queryEndTime;
        this.queryStatsEntries = queryStatsEntries;
    }

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

    public Boolean getSearchParametersAndRelation() {
        return searchParametersAndRelation;
    }

    public void setSearchParametersAndRelation(Boolean searchParametersAndRelation) {
        this.searchParametersAndRelation = searchParametersAndRelation;
    }

    public List<QueryStatsEntry> getQueryStatsEntries() {
        return queryStatsEntries;
    }

    public void setQueryStatsEntries(List<QueryStatsEntry> queryStatsEntries) {
        this.queryStatsEntries = queryStatsEntries;
    }
}
