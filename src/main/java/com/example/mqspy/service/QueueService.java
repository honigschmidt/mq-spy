package com.example.mqspy.service;

import com.example.mqspy.model.Queue;
import com.example.mqspy.model.*;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class QueueService {

    private final QueueRepository queueRepository;
    private final QueryRepository queryRepository;
    private final QueryStatsEntryRepository queryStatsEntryRepository;
    private final QueueManagerService queueManagerService;
    private final ZoneId zoneId = ZoneId.of("Europe/Berlin");

    public QueueService(QueueRepository queueRepository, QueryRepository queryRepository, QueryStatsEntryRepository queryStatsEntryRepository, QueueManagerService queueManagerService) {
        this.queueRepository = queueRepository;
        this.queryRepository = queryRepository;
        this.queryStatsEntryRepository = queryStatsEntryRepository;
        this.queueManagerService = queueManagerService;
    }

    public void addQueue(Queue queue) {
        queueRepository.save(queue);
    }

    public List<Message> browseQueues(Query query) throws RuntimeException {

        String queryId = query.getQueryId();
        String environment = query.getEnvironment();
        List<String> queueNames = query.getQueueNames();
        List<String> searchParameters = query.getSearchParameters();
        LocalDateTime timeStampFrom = query.getTimeStampFrom();
        LocalDateTime timeStampTo = query.getTimeStampTo();
        Boolean searchParametersAndRelation = query.getSearchParametersAndRelation();
        Boolean getOnlyQueueDepth = query.getGetOnlyQueueDepth();
        query.setQueryStartTime(LocalDateTime.now());

        List<Message> messageList = new ArrayList<>();

        for (String queueName : queueNames) {

            Set<QueueManager> queueManagers = queueManagerService.getQueueManagersByQueueName(queueName);

            for (QueueManager queueManager : queueManagers) {

                if (queueManager.getEnvironment().equals(environment)) {

                    Integer queueDepth = getQueueDepth(queueName, queueManager.getName());

                    QueryStatsEntry queryStatsEntry = new QueryStatsEntry();
                    Integer matchingMessages = 0;
                    queryStatsEntry.setQueryId(queryId);
                    queryStatsEntry.setQueueName(queueName);
                    queryStatsEntry.setQueueManagerName(queueManager.getName());
                    queryStatsEntry.setQueueDepth(queueDepth);
                    QueryStatsEntry savedEntry = queryStatsEntryRepository.save(queryStatsEntry);

                    if (!getOnlyQueueDepth) {
                        boolean isFirstMessage = true;
                        boolean isDone = false;
                        try {
                            MQQueueManager mqQueueManager = new MQQueueManager(queueManager.getName(), queueManagerService.setConnectionProperties(queueManager.getName()));
                            int queueOpenOptions = MQConstants.MQOO_BROWSE;
                            MQQueue queue = mqQueueManager.accessQueue(queueName, queueOpenOptions);
                            MQMessage mqMessage = new MQMessage();
                            MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
                            while (!isDone) {
                                boolean searchParameterMatch = false;
                                boolean timeStampMatch = false;
                                boolean addMessageToList = false;
                                if (isFirstMessage) {
                                    getMessageOptions.options = MQConstants.MQGMO_BROWSE_FIRST + MQConstants.MQGMO_WAIT + CMQC.MQGMO_PROPERTIES_FORCE_MQRFH2;
                                    isFirstMessage = false;
                                } else {
                                    getMessageOptions.options = MQConstants.MQGMO_BROWSE_NEXT + MQConstants.MQGMO_WAIT + CMQC.MQGMO_PROPERTIES_FORCE_MQRFH2;
                                }
                                queue.get(mqMessage, getMessageOptions);

                                mqMessage.seek(0);
                                String rfhStrucId = mqMessage.readStringOfByteLength(4);
                                mqMessage.seek(8);
                                Integer rfhStrucLength = mqMessage.readInt();

                                byte[] byteMessage = new byte[mqMessage.getMessageLength()];
                                String messageHeader = null;

                                if (rfhStrucId.trim().equalsIgnoreCase("rfh") || rfhStrucId.trim().equalsIgnoreCase("rfh2")) {
                                    mqMessage.seek(0);
                                    mqMessage.readFully(byteMessage);
                                    byte[] byteHeader = Arrays.copyOfRange(byteMessage, 0, rfhStrucLength);
                                    byteMessage = Arrays.copyOfRange(byteMessage, rfhStrucLength, mqMessage.getMessageLength());
                                    messageHeader = new String(byteHeader);
                                } else {
                                    mqMessage.seek(0);
                                    mqMessage.readFully(byteMessage);
                                }
                                String messagePayload = new String(byteMessage);

                                String messageId = Hex.encodeHexString(mqMessage.messageId);
                                String correlationId = Hex.encodeHexString(mqMessage.correlationId);

                                if (Objects.nonNull(searchParameters)) {
                                    List<String> cleanedSearchParameters = new ArrayList<>();

                                    for (String searchParameter : searchParameters) {
                                        if (!searchParameter.isBlank()) {
                                            cleanedSearchParameters.add(searchParameter.trim().toLowerCase());
                                        }
                                    }

                                    String fullMessageText = messageId + correlationId + messagePayload;

                                    if (Objects.nonNull(messageHeader)) {
                                        fullMessageText += messageHeader;
                                    }

                                    fullMessageText = fullMessageText.toLowerCase();

                                    if (!searchParametersAndRelation) {
                                        for (String searchParameter : cleanedSearchParameters) {
                                            if (fullMessageText.contains(searchParameter)) {
                                                searchParameterMatch = true;
                                                break;
                                            }
                                        }
                                    }

                                    if (searchParametersAndRelation) {
                                        searchParameterMatch = true;

                                        for (String searchParameter : cleanedSearchParameters) {
                                            if (!fullMessageText.contains(searchParameter)) {
                                                searchParameterMatch = false;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (Objects.nonNull(timeStampFrom) || Objects.nonNull(timeStampTo)) {
                                    LocalDateTime messageTimeStamp = mqMessage.putDateTime.toZonedDateTime().toLocalDateTime().plusHours(timeOffset(zoneId));
                                    if (Objects.nonNull(timeStampFrom) && Objects.isNull(timeStampTo)) {
                                        if (messageTimeStamp.isAfter(timeStampFrom)) {
                                            timeStampMatch = true;
                                        }
                                    }
                                    if (Objects.isNull(timeStampFrom) && Objects.nonNull(timeStampTo)) {
                                        if (messageTimeStamp.isBefore(timeStampTo)) {
                                            timeStampMatch = true;
                                        }
                                    }
                                    if (Objects.nonNull(timeStampFrom) && Objects.nonNull(timeStampTo)) {
                                        if (messageTimeStamp.isAfter(timeStampFrom) && messageTimeStamp.isBefore(timeStampTo)) {
                                            timeStampMatch = true;
                                        }
                                    }
                                }

                                if (Objects.isNull(searchParameters) && Objects.isNull(timeStampFrom) && Objects.isNull(timeStampTo)) {
                                    addMessageToList = true;
                                }

                                if (Objects.isNull(searchParameters) && timeStampMatch) {
                                    addMessageToList = true;
                                }

                                if (searchParameterMatch && Objects.isNull(timeStampFrom) && Objects.isNull(timeStampTo)) {
                                    addMessageToList = true;
                                }

                                if (searchParameterMatch && timeStampMatch) {
                                    addMessageToList = true;
                                }

                                if (addMessageToList) {
                                    Message message = new Message();
                                    message.setPutTimeStamp(mqMessage.putDateTime.toZonedDateTime().toLocalDateTime().plusHours(timeOffset(zoneId)));
                                    message.setSourceQueue(queueName);
                                    message.setSourceQueueManager(queueManager.getName());
                                    message.setMessageId(messageId);
                                    message.setCorrelationId(correlationId);
                                    message.setMessageHeader(messageHeader);
                                    message.setMessagePayload(messagePayload);
                                    messageList.add(message);
                                    matchingMessages ++;
                                }
                                mqMessage.clearMessage();
                                mqMessage.correlationId = MQConstants.MQCI_NONE;
                                mqMessage.messageId = MQConstants.MQMI_NONE;
                            }
                        } catch (MQException mqException) {
                            if (mqException.reasonCode == 2033) {
                                isDone = true;
                            }
                        } catch (IOException ioException) {
                            throw new RuntimeException(ioException);
                        }
                        savedEntry.setMatchingMessagesCount(matchingMessages);
                    }

                    if (getOnlyQueueDepth) {
                        savedEntry.setMatchingMessagesCount(0);
                    }
                    queryStatsEntryRepository.save(savedEntry);
                }
            }
        }
        query.setQueryEndTime(LocalDateTime.now());
        queryRepository.save(query);
        return messageList;
    }

    public Integer getQueueDepth(String queueName, String queueManagerName) {
        int queueDepth;

        QueueManager queueManager = queueManagerService.getQueueManagerByName(queueManagerName);
        try {
            MQQueueManager mqQueueManager = new MQQueueManager(queueManager.getName(), queueManagerService.setConnectionProperties(queueManager.getName()));
            int queueOpenOptions = MQConstants.MQOO_INQUIRE;
            MQQueue mqQueue = mqQueueManager.accessQueue(queueName, queueOpenOptions);
            queueDepth = mqQueue.getCurrentDepth();
            mqQueue.close();
            mqQueueManager.disconnect();
        } catch (MQException mqException) {
            throw new RuntimeException(mqException);
        }
        return queueDepth;
    }

    public List<String> getQueueList(String environment) throws NoSuchElementException {
        Set<QueueManager> queueManagers = queueManagerService.getQueueManagersByEnvironment(environment);
        List<String> queueList = new ArrayList<>();
        for (QueueManager queueManager : queueManagers) {
            Long queueManagerId = queueManager.getId();
            Set<Queue> queues = queueRepository.findByQueueManagers_Id(queueManagerId).orElseThrow();
            for (Queue queue : queues) {
                if (!queueList.contains(queue.getName())) {
                    queueList.add(queue.getName());
                }
            }
        }
        return queueList;
    }

    public QueryStats getQueryStatistics(String queryId) throws NoSuchElementException {
        Query query = queryRepository.findByQueryId(queryId).orElseThrow();
        List<QueryStatsEntry> queryStatsEntries = queryStatsEntryRepository.findByQueryId(queryId).orElseThrow();
        return new QueryStats(
                queryId,
                query.getEnvironment(),
                query.getSearchParameters(),
                query.getTimeStampFrom(),
                query.getTimeStampTo(),
                query.getSearchParametersAndRelation(),
                query.getGetOnlyQueueDepth(),
                query.getQueryStartTime(),
                query.getQueryEndTime(),
                queryStatsEntries
        );
    }

    public Integer timeOffset(ZoneId zoneId) {
        if (zoneId.getRules().isDaylightSavings(Instant.now())) {
            return 2;
        } else {
            return 1;
        }
    }
}