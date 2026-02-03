package com.example.mqspy.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "queues")
public class Queue {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "queues_queuemanagers", // Name of the join table
            joinColumns = @JoinColumn(name = "queue_id"), // Define joined id
            inverseJoinColumns = @JoinColumn(name = "queuemanager_id")) // Define joined id
    private Set<QueueManager> queueManagers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QueueManager> getQueueManagers() {
        return queueManagers;
    }

    public void setQueueManagers(Set<QueueManager> queueManagers) {
        this.queueManagers = queueManagers;
    }
}
