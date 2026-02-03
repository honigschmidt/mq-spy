package com.example.mqspy.controller;

import com.example.mqspy.model.*;
import com.example.mqspy.service.QueueManagerService;
import com.example.mqspy.service.QueueService;
import com.ibm.mq.MQException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
public class APIController {

    private final QueueManagerService queuemanagerService;
    private final QueueService queueService;
    private final EnvironmentRepository environmentRepository;
    private final QueryStatsEntryRepository queryStatsEntryRepository;

    public APIController(QueueManagerService queuemanagerService, QueueService queueService, EnvironmentRepository environmentRepository, QueryStatsEntryRepository queryStatsEntryRepository) {
        this.queuemanagerService = queuemanagerService;
        this.queueService = queueService;
        this.environmentRepository = environmentRepository;
        this.queryStatsEntryRepository = queryStatsEntryRepository;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    private ResponseEntity<String> getIndexHtml() {
        ClassPathResource classPathResource = new ClassPathResource("static/index.html");
        try {
            String indexHtml = StreamUtils.copyToString(classPathResource.getInputStream(), StandardCharsets.UTF_8);
            return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(indexHtml);
        } catch (IOException e) {
            throw new NoSuchElementException();
        }
    }

    @GetMapping(value = "/api/testqm", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> testqm(@RequestParam String queuemanagername) {
        try {
            if (queuemanagerService.testQueueManager(queuemanagername)) {
                return ResponseEntity.ok("Connection to queue manager successful.");
            }
        } catch (MQException mqException) {
            return ResponseEntity.ok("Connection to queue manager is refused.");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.ok("Unknown queue manager.");
        } return null;
    }

    @PostMapping(value = "/api/browseqs", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Message>> browseqs(@RequestBody Query query) {
        LocalDateTime timeStampFrom = query.getTimeStampFrom();
        LocalDateTime timeStampTo = query.getTimeStampTo();
        if (Objects.nonNull(timeStampFrom) && Objects.nonNull(timeStampTo)) {
            if (timeStampTo.isBefore(timeStampFrom) || timeStampTo.isEqual(timeStampFrom)) {
                return ResponseEntity.unprocessableEntity().build();
            }
        }
        try {
            List<Message> response = queueService.browseQueues(query);
            if (!response.isEmpty()) {
                return ResponseEntity.ok(response);
            }
            if (query.getGetOnlyQueueDepth()) {
                return ResponseEntity.status(HttpStatusCode.valueOf(206)).build();
            }
            else {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException();
        }
    }

    @GetMapping(value = "/api/getquerystats", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QueryStats> getQueryStatistics(@RequestParam String queryid) {
        try {
            QueryStats queryStats = queueService.getQueryStatistics(queryid);
            return ResponseEntity.ok(queryStats);
        } catch (NoSuchElementException noSuchElementException) {
            throw new NoSuchElementException();
        }
    }

    @GetMapping(value = "/api/getquerystatus", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<QueryStatsEntry> getQueryStatus(@RequestParam String queryid) {
        QueryStatsEntry queryStatsEntry = queryStatsEntryRepository.findTopByQueryIdOrderByIdDesc(queryid).orElseThrow();
        return ResponseEntity.ok(queryStatsEntry);
    }

    @GetMapping(value = "/api/getqlist", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<String>> getQueueList(@RequestParam String environment) {
        try {
            List<String> queueList = queueService.getQueueList(environment);
            return ResponseEntity.ok(queueList);
        } catch (NoSuchElementException noSuchElementException) {
            throw new NoSuchElementException();
        }
    }

    @GetMapping(value = "/api/getenvlist", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<String>> getEnvironmentList() {
        List<String> environmentList = environmentRepository.findAllNames().orElseThrow();
        return ResponseEntity.ok(environmentList);
    }
}