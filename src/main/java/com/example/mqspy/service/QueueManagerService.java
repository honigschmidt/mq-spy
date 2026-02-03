package com.example.mqspy.service;

import com.example.mqspy.model.QueueManager;
import com.example.mqspy.model.QueueManagerRepository;
import com.example.mqspy.model.QueueRepository;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class QueueManagerService {

    private final QueueManagerRepository queueManagerRepository;
    private final QueueRepository queueRepository;

    public QueueManagerService(QueueManagerRepository queueManagerRepository, QueueRepository queueRepository) {
        this.queueManagerRepository = queueManagerRepository;
        this.queueRepository = queueRepository;
    }

    public void addQueueManager(QueueManager queueManager) {
        queueManagerRepository.save(queueManager);
    }

    public QueueManager getQueueManagerByName(String queueManagerName) throws NoSuchElementException {
        return queueManagerRepository.findByname(queueManagerName).orElseThrow();
    }

    public Set<QueueManager> getQueueManagersByEnvironment(String environment) {
        return queueManagerRepository.findByEnvironment(environment).orElseThrow();
    }

    public Set<QueueManager> getQueueManagersByQueueName(String queueName) {
        Long queueId = queueRepository.findByName(queueName).orElseThrow().getId();
        return queueManagerRepository.findByQueues_Id(queueId).orElseThrow();
    }

    public boolean testQueueManager(String queueManagerName) throws MQException, NoSuchElementException {
        MQQueueManager mqQueueManager = new MQQueueManager(queueManagerName, setConnectionProperties(queueManagerName));
        mqQueueManager.disconnect();
        return true;
    }

    public Hashtable<String, Object> setConnectionProperties(String queueManagerName) throws NoSuchElementException {
        Hashtable<String, Object> connectionProperties = new Hashtable<>();
        QueueManager queueManager = queueManagerRepository.findByname(queueManagerName).orElseThrow();
        connectionProperties.put(CMQC.HOST_NAME_PROPERTY, queueManager.getHost());
        connectionProperties.put(CMQC.PORT_PROPERTY, queueManager.getPort());
        connectionProperties.put(CMQC.CHANNEL_PROPERTY, queueManager.getChannel());
        connectionProperties.put(CMQC.USER_ID_PROPERTY, queueManager.getUserID());
        connectionProperties.put(CMQC.PASSWORD_PROPERTY, queueManager.getPassword());
        return connectionProperties;
    }
}
