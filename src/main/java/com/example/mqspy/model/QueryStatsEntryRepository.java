package com.example.mqspy.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QueryStatsEntryRepository extends CrudRepository<QueryStatsEntry, Long> {
    Optional<List<QueryStatsEntry>> findByQueryId(String queryId);
    Optional<QueryStatsEntry> findTopByQueryIdOrderByIdDesc(String queryId);
}
