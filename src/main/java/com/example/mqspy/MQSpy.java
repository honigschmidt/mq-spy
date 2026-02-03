package com.example.mqspy;

import com.example.mqspy.model.Environment;
import com.example.mqspy.model.EnvironmentRepository;
import com.example.mqspy.model.Queue;
import com.example.mqspy.model.QueueManager;
import com.example.mqspy.service.QueueManagerService;
import com.example.mqspy.service.QueueService;
import com.example.mqspy.service.ResourceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
public class MQSpy {

    private final QueueManagerService queuemanagerService;
    private final QueueService queueService;
    private final ResourceService resourceService;
    private final EnvironmentRepository environmentRepository;

    private final String queueManagerConfig = "qmconfig.json";
    private final String queueConfig = "qconfig.json";
    private final String queueManagerList = "qmgrList";

    public static void main(String[] args) {
        SpringApplication.run(MQSpy.class, args);
    }

    public MQSpy(QueueManagerService queuemanagerService, QueueService queueService, ResourceService resourceService, EnvironmentRepository environmentRepository) {
        this.queuemanagerService = queuemanagerService;
        this.queueService = queueService;
        this.resourceService = resourceService;
        this.environmentRepository = environmentRepository;
    }

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterFilterRegistrationBean() {
        FilterRegistrationBean<RateLimitingFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RateLimitingFilter());
        filterFilterRegistrationBean.addUrlPatterns("/api/*");
        filterFilterRegistrationBean.setOrder(0);
        filterFilterRegistrationBean.setInitParameters(Map.of("maxRequestPerMinute", "90"));
        return filterFilterRegistrationBean;
    }

    @PostConstruct
    private void init() {
        loadQueueManagerData();
        loadQueueData();
        loadEnvironmentData();
    }

    private void loadQueueManagerData() {
        ObjectNode queueManagerRootNode = (ObjectNode) resourceService.readJSON(queueManagerConfig);
        Iterator<String> queueManagerNodeIterator = queueManagerRootNode.fieldNames();
        queueManagerNodeIterator.forEachRemaining(queueManagerName -> {
            JsonNode queueManagerNode = queueManagerRootNode.get(queueManagerName);
            QueueManager queuemanager = new QueueManager();
            queuemanager.setName(queueManagerName);
            queuemanager.setHost(queueManagerNode.get("host").asText());
            queuemanager.setPort(queueManagerNode.get("port").asInt());
            queuemanager.setChannel(queueManagerNode.get("channel").asText());
            queuemanager.setUserID(queueManagerNode.get("user_id").asText());
            queuemanager.setPassword(queueManagerNode.get("password").asText());
            queuemanager.setEnvironment(queueManagerNode.get("environment").asText());
            queuemanager.setActive(queueManagerNode.get("active").asBoolean());
            queuemanagerService.addQueueManager(queuemanager);
        });
    }

    private void loadQueueData() {
        ObjectNode queueRootNode = (ObjectNode) resourceService.readJSON(queueConfig);
        Iterator<String> queueNodeIterator = queueRootNode.fieldNames();
        queueNodeIterator.forEachRemaining(queueName -> {
            Queue queue = new Queue();
            Set<QueueManager> queueManagerSet = new HashSet<>();
            JsonNode queueNode = queueRootNode.get(queueName);
            ArrayNode queueManagerNode = (ArrayNode) queueNode.get(queueManagerList);
            for (JsonNode queueManager : queueManagerNode) {
                queueManagerSet.add(queuemanagerService.getQueueManagerByName(queueManager.asText()));
            }
            queue.setName(queueName);
            queue.setQueueManagers(queueManagerSet);
            queueService.addQueue(queue);
        });
    }

    private void loadEnvironmentData() {
        List<String> environmentList = new ArrayList<>();
        ObjectNode queueManagerRootNode = (ObjectNode) resourceService.readJSON(queueManagerConfig);
        Iterator<String> queueManagerNodeIterator = queueManagerRootNode.fieldNames();
        queueManagerNodeIterator.forEachRemaining(queueManagerName -> {
            JsonNode queueManagerNode = queueManagerRootNode.get(queueManagerName);
            String environmentName = queueManagerNode.get("environment").asText();
            Boolean isEnvironmentActive = queueManagerNode.get("active").asBoolean();
            if (!environmentList.contains(environmentName) && !environmentName.equals("DEV") && isEnvironmentActive) {
                environmentList.add(environmentName);
            }
        });
        for (String environmentName : environmentList) {
            Environment environment = new Environment(environmentName);
            environmentRepository.save(environment);
        }
    }
}
