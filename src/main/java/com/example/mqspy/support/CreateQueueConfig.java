package com.example.mqspy.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.pcf.PCFMessage;
import com.ibm.mq.headers.pcf.PCFMessageAgent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CreateQueueConfig {

    static String queueManagerConfigPath = "qmconfig.json";
    static String queueConfigPath = System.getProperty("user.home") + File.separator + "qconfig.json";
    static Hashtable<String, Object> connectionProperties = new Hashtable<>();
    static String queueConfigArray = "qmgrList";
    static ObjectNode queueConfig = null;
    static ObjectNode queueManagerConfig = null;
    static List<String> queueNameFilters = Arrays.asList("");

    public static void main(String[] args) {
        createQueueConfig();
    }

    public static void createQueueConfig() {
        List<String> queueManagerNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            queueManagerConfig = (ObjectNode) readJSONFromResource(queueManagerConfigPath);
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException(runtimeException);
        }
        Iterator<String> iterator = queueManagerConfig.fieldNames();
        iterator.forEachRemaining(queueManagerName -> {
            if ((queueManagerConfig.get(queueManagerName)).get("active").asBoolean()) {
                queueManagerNames.add(queueManagerName);
            }
        });
        try {
            queueConfig = (ObjectNode) objectMapper.readTree(readFileFromFS(queueConfigPath));
            System.out.println("Queue configuration already exist, file loaded.");
        } catch (RuntimeException | IOException exception) {
            queueConfig = objectMapper.createObjectNode();
            System.out.println("Queue configuration not found, a new file will be created.");
        }
        for (String queueManagerName : queueManagerNames) {
            System.out.println("Getting queue list from " + queueManagerName + "...");
            try {
                List<String> queueNames = getQueueNames(queueManagerName);
                for (String queueName : queueNames) {
                    for (String queueNameFilter : queueNameFilters) {
                        if (queueName.contains(queueNameFilter)) {
                            if (queueConfig.has(queueName)) {
                                ArrayNode queueManagerNodeList = (ArrayNode) queueConfig.at("/" + queueName).get(queueConfigArray);
                                List<String> queueManagerList = new ArrayList<>();
                                for (JsonNode queueManagerNode : queueManagerNodeList) {
                                    queueManagerList.add(queueManagerNode.asText());
                                }
                                if (!queueManagerList.contains(queueManagerName)) {
                                    queueManagerNodeList.add(queueManagerName);
                                }
                            } else {
                                ObjectNode childNode = objectMapper.createObjectNode();
                                queueConfig.set(queueName, childNode);
                                ArrayList<String> arrayList = new ArrayList<>();
                                arrayList.add(queueManagerName);
                                ArrayNode arrayNode = objectMapper.valueToTree(arrayList);
                                childNode.putArray(queueConfigArray).addAll(arrayNode);
                            }
                        }
                    }
                }
            } catch (RuntimeException runtimeException) {
                System.out.println("Error: can't get queue list from " + queueManagerName + ".");
            }
        }
        if (!queueConfig.isEmpty()) {
            try {
                writeFileToFS(queueConfigPath, prettyPrintJSON(objectMapper.writeValueAsString(queueConfig)));
            } catch (JsonProcessingException jsonProcessingException) {
                throw new RuntimeException(jsonProcessingException);
            }
        }
    }

    public static List<String> getQueueNames(String queueManagerName) {
        JsonNode connectionPropertiesNode = queueManagerConfig.get(queueManagerName);
        connectionProperties.put(CMQC.HOST_NAME_PROPERTY, connectionPropertiesNode.get("host").asText());
        connectionProperties.put(CMQC.PORT_PROPERTY, connectionPropertiesNode.get("port").asInt());
        connectionProperties.put(CMQC.CHANNEL_PROPERTY, connectionPropertiesNode.get("channel").asText());
        connectionProperties.put(CMQC.USER_ID_PROPERTY, connectionPropertiesNode.get("user_id").asText());
        connectionProperties.put(CMQC.PASSWORD_PROPERTY, connectionPropertiesNode.get("password").asText());
        List<String> queueNames = new ArrayList<>();
        try {
            MQQueueManager queueManager = new MQQueueManager(queueManagerName, connectionProperties);
            PCFMessageAgent pcfMessageAgent = new PCFMessageAgent(queueManager);
            PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);
            request.addParameter(CMQC.MQCA_Q_NAME, "*");
            request.addParameter(CMQC.MQIA_Q_TYPE, CMQC.MQQT_LOCAL);
            request.addParameter(CMQCFC.MQIACF_Q_ATTRS, new int[]{CMQC.MQCA_Q_NAME});
            PCFMessage[] responses = pcfMessageAgent.send(request);
            for (PCFMessage response : responses) {
                String queueName = response.getStringParameterValue(CMQC.MQCA_Q_NAME);
                queueNames.add(queueName.trim());
            }
            pcfMessageAgent.disconnect();
            queueManager.disconnect();
            return queueNames;
        } catch (MQException | MQDataException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static JsonNode readJSONFromResource(String resourceName) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        try {
            return new ObjectMapper().readTree(inputStream);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static String prettyPrintJSON(String unformattedJSON) {
        try {
            return new ObjectMapper().readTree(unformattedJSON).toPrettyString();
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }

    public static String readFileFromFS(String filePath) throws IOException {
        Path path = Path.of(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    public static void writeFileToFS(String filePath, String fileContent) {
        try {
            Files.writeString(Path.of(filePath), fileContent);
            System.out.print("\n---> " + filePath + "\n");
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}