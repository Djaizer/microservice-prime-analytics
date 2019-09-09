package com.analytics.prime.demoshell;


import com.analytics.prime.demoshell.kafka.constants.IKafkaConstants;
import com.analytics.prime.demoshell.kafka.consumer.ConsumerCreator;
import com.analytics.prime.demoshell.kafka.pojo.MessageType;
import com.analytics.prime.demoshell.kafka.pojo.ServerObject;
import com.analytics.prime.demoshell.kafka.pojo.UserObject;
import com.analytics.prime.demoshell.kafka.producer.ProducerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaService {


    static private Map<Integer, Producer<Long, UserObject>> producerUserPool = new HashMap();
    static private Map<Integer, Producer<Long, ServerObject>> producerServerPool = new HashMap();
    static private Map<Integer, Consumer<Long, ServerObject>> consumerUserPool = new HashMap();
    static private Map<Integer, Consumer<Long, UserObject>> consumerServerPool = new HashMap();

    private Map<Integer, List<UserObject>> serverTasks = new HashMap<>();

    private static Set<String> responses = new HashSet<>();

    @Autowired
    PrimeService primeService;

    @Autowired
    ReportService reportService;


    public void getReportStatus(int userID) {
        UserObject status = new UserObject();
        status.setType(MessageType.REPORT_STATUS);
        final ProducerRecord<Long, UserObject> record = new ProducerRecord<Long, UserObject>(serverTopic(userID), status);
        send(producerUserPool.get(userID), record);
        CompletableFuture.supplyAsync(() -> runServerConsumer(userID));
    }

    public void getReport(int userID) {
        UserObject sendNumber = new UserObject();
        sendNumber.setType(MessageType.REPORT);
        final ProducerRecord<Long, UserObject> record = new ProducerRecord<Long, UserObject>(serverTopic(userID), sendNumber);
        send(producerUserPool.get(userID), record);
        CompletableFuture.supplyAsync(() -> runServerConsumer(userID));

    }


    public void sendNumber(int userID, int number) {

        //   runKafka(userID, number);
        CompletableFuture.supplyAsync(() -> runUserProducer(userID, number));


    }


    private String runUserProducer(int userID, int number) {
        if (!producerUserPool.containsKey(userID)) {
            producerUserPool.put(userID, ProducerCreator.createUserProducer(userClientId(userID)));
        }
        UserObject sendNumber = new UserObject();
        sendNumber.setType(MessageType.NUMBER);
        sendNumber.setNumber(number);
        final ProducerRecord<Long, UserObject> record = new ProducerRecord<Long, UserObject>(serverTopic(userID), sendNumber);
        send(producerUserPool.get(userID), record);

        CompletableFuture.supplyAsync(() -> runServerConsumer(userID));
        return "runUserProducer";
    }


    private void runKafka(int userID, int number) {

        CompletableFuture.supplyAsync(() -> runUserProducer(userID, number));
        CompletableFuture.supplyAsync(() -> runServerConsumer(userID));
        CompletableFuture.supplyAsync(() -> runServerProducer(userID));
        CompletableFuture.supplyAsync(() -> runUserConsumer(userID));


    }

    private String runServerConsumer(int userID) {
        if (!consumerServerPool.containsKey(userID)) {
            consumerServerPool.put(userID, ConsumerCreator.createServerConsumer(serverTopic(userID)));
        }
        Consumer<Long, UserObject> consumer = consumerServerPool.get(userID);
        int noMessageToFetch = 0;

        while (true) {
            final ConsumerRecords<Long, UserObject> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                if (!serverTasks.containsKey(userID)) {
                    serverTasks.put(userID, new ArrayList<UserObject>() {{
                        record.value();
                    }});
                } else {
                    serverTasks.get(userID).add(record.value());
                }
            });
            consumer.commitAsync();
        }
        CompletableFuture.supplyAsync(() -> runServerProducer(userID));

        consumer.close();
        consumerServerPool.remove(userID);

        return "runServerConsumer";
    }


    private String runServerProducer(Integer userId) {
        if (!producerServerPool.containsKey(userId)) {
            producerServerPool.put(userId, ProducerCreator.createServerProducer(serverClientId(userId)));
        }
        Producer<Long, ServerObject> producer = producerServerPool.get(userId);

        serverTasks.get(userId).forEach(task -> {
            ServerObject serverResponse = new ServerObject();
            if (task.getType() == MessageType.NUMBER) {
                serverResponse.setType(MessageType.NUMBER);
                serverResponse.setNumber(primeService.getPrime(task.getNumber(), userId));

                final ProducerRecord<Long, ServerObject> record = new ProducerRecord<>(userTopic(userId), serverResponse);
                send(producer, record);
            }

            if (task.getType() == MessageType.REPORT) {
                serverResponse.setType(MessageType.REPORT);
                serverResponse.setReport(reportService.getReport(userId));
                final ProducerRecord<Long, ServerObject> record = new ProducerRecord<>(userTopic(userId), serverResponse);
                send(producer, record);
            }

            if (task.getType() == MessageType.REPORT_STATUS) {
                serverResponse.setType(MessageType.REPORT_STATUS);
                serverResponse.setTimeLeft(reportService.getStatus());
                final ProducerRecord<Long, ServerObject> record = new ProducerRecord<>(userTopic(userId), serverResponse);
                send(producer, record);
            }

        });
        CompletableFuture.supplyAsync(() -> runUserConsumer(userId));
        return "runServerProducer";

    }

    private String runUserConsumer(int userId) {
        if (!consumerUserPool.containsKey(userId)) {
            consumerUserPool.put(userId, ConsumerCreator.createUserConsumer(userTopic(userId)));
        }
        Consumer<Long, ServerObject> consumer = consumerUserPool.get(userId);
        int noMessageToFetch = 0;

        while (true) {
            final ConsumerRecords<Long, ServerObject> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
                if (noMessageToFetch > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                    break;
                else
                    continue;
            }

            consumerRecords.forEach(record -> {
                ServerObject response = record.value();
                if (response.getType() == MessageType.NUMBER) {
                    String responseFormat = String.format("[PRIME] User %d gets a prime nimber: %s", userId, response.getNumber());
                    if (!responses.contains(responseFormat)) {
                        System.out.println(responseFormat);
                        responses.add(responseFormat);
                    }
                }

                if (response.getType() == MessageType.REPORT_STATUS) {
                    String responseFormat = String.format("[REPORT STATUS] User %d The report will be ready in %s", userId, response.getTimeLeft());
                    if (!responses.contains(responseFormat)) {
                        System.out.println(responseFormat);
                        responses.add(responseFormat);
                    }
                }

                if (response.getType() == MessageType.REPORT) {
                    String responseFormat = String.format("[REPORT] User %d . Your report is ready: %s", userId, response.getReport());
                    if (!responses.contains(responseFormat)) {
                        System.out.println(responseFormat);
                        responses.add(responseFormat);
                    }
                }

            });

            consumer.commitAsync();
        }
        consumer.close();
        consumerUserPool.remove(userId);
        return "runUserConsumer";
    }

    private void send(Producer producer, ProducerRecord record) {
        try {
            producer.send(record).get();
        } catch (ExecutionException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private static String userTopic(int userID) {
        return "UserTopic_" + userID;
    }

    private static String serverTopic(int userID) {
        return "ServerTopic_" + userID;
    }


    private static String userGroup(int userID) {
        return "userGroup" + userID;
    }

    private static String serverClientId(int userID) {
        return "serverClientId" + userID;
    }

    private static String userClientId(int userID) {
        return "userClientId" + userID;
    }

    public boolean isUserNotExist(int userID) {
        return !producerUserPool.containsKey(userID);
    }


}
