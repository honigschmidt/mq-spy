package com.example.mqspy.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface QueueManagerRepository extends CrudRepository<QueueManager, Long> {
    Optional<QueueManager> findByname(String name);
    Optional<Set<QueueManager>> findByQueues_Id(Long queue_id);
    Optional<Set<QueueManager>> findByEnvironment(String environment);
}
