package com.example.mqspy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "querystatsentries")
public class QueryStatsEntry {

    @Id
    @GeneratedValue
    private Long id;

    private String queryId;
    private String queueName;
    private String queueManagerName;
    private Integer queueDepth;
    private Integer matchingMessagesCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueManagerName() {
        return queueManagerName;
    }

    public void setQueueManagerName(String queueManagerName) {
        this.queueManagerName = queueManagerName;
    }

    public Integer getQueueDepth() {
        return queueDepth;
    }

    public void setQueueDepth(Integer queueDepth) {
        this.queueDepth = queueDepth;
    }

    public Integer getMatchingMessagesCount() {
        return matchingMessagesCount;
    }

    public void setMatchingMessagesCount(Integer matchingMessagesCount) {
        this.matchingMessagesCount = matchingMessagesCount;
    }
}
