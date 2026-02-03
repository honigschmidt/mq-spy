### MQ-Spy: Enterprise Middleware Observability & Diagnostic Engine

**MQ-Spy** is a high-performance, non-destructive diagnostic platform engineered for deep-packet inspection and real-time forensics within IBM MQ ecosystems. Designed to bridge the gap between legacy middleware and modern observability, it provides a robust interface for auditing complex message flows without impacting production stability.

### Key Capabilities

+ **Non-Destructive Forensic Auditing:** Browse and inspect live queue content across multiple environments (DEV, PROD, etc.) using native MQI protocols.
+ **Advanced Message Tracing:** Extract and visualize full-spectrum metadata, including PutTime, Source Queue, Message ID, Correlation ID, and RFH2 headers.
+ **Intelligent Payload Analysis:** Integrated schema-aware rendering and "pretty-print" formatting for complex JSON and XML data structures.
+ **Dynamic Topology Discovery:** Automated crawler to map queue manager hierarchies and manage distributed queue configurations.
+ **Multi-Criteria Search:** Rapid root-cause analysis via temporal (timestamp) and metadata-driven filtering.
+ **Artifact Export:** Securely persist message snapshots in structured (JSON, XML) or raw text formats for external forensic review

Please refer to the included screen capture for an overview of the message inspection interface.

### Technical Architecture

+ **Backend:** Java 21 Microservice Core utilizing Spring Boot 3.x for high-concurrency message handling.
+ **Frontend:** Zero-Dependency Native ES6+ architecture, optimized for performance and security in restricted enterprise networks.
+ **Persistence:** H2 In-Memory Layer for high-speed session state and configuration caching.
+ **Messaging:** Optimized IBM MQ Native Integration (MQI) for low-overhead, read-only queue interaction.
+ **Delivery Model:** Packaged as a self-contained Uber-JAR (Portable Executable) for zero-config deployment across heterogeneous infrastructure.

### Deployment & Initialization

**1. Infrastructure Mapping**

Define your target environment parameters in src/main/resources/qmconfig.json. (See the included example for schema details).

**2. Topology Discovery**

Execute the CreateQConfig.java utility located under the support package. This will:
+ Authenticate and connect to specified queue managers.
+ Crawl the system to discover available queues.
+ Generate a qconfig.json topology map in your HOME directory.

Move this generated map to the resources directory before launching the main engine.

**3. Execution**

Launch the engine by running MQSpy.java or executing the standalone Uber-JAR artifact.

### Local Sandbox & Development
For local testing and validation, a pre-configured environment is compatible with the  [**IBM MQ container suite**](https://developer.ibm.com/tutorials/mq-connect-app-queue-manager-containers/).

**System Authorization Setup:**

After starting your container, ensure the following permissions are granted for diagnostic access:
+ setmqaut -m QM1 -t queue -n SYSTEM.ADMIN.COMMAND.QUEUE -g nobody +inq +put
+ setmqaut -m QM1 -t queue -n SYSTEM.DEFAULT.MODEL.QUEUE -g nobody +get +inq +browse