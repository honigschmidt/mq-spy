package com.example.mqspy.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QueryRepository extends CrudRepository<Query, Long> {
    Optional<Query> findByQueryId (String queryId);
}
