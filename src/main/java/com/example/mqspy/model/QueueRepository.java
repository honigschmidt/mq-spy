package com.example.mqspy.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QueueRepository extends CrudRepository<Queue, Long> {
    Optional<Queue> findByName(String name);
    Optional<Set<Queue>> findByQueueManagers_Id(Long queuemanager_id);

    @Query("SELECT name FROM Queue")
    Optional<List<String>> findAllNames();
}
