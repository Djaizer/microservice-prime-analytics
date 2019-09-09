package com.analytics.prime.demoshell.kafka.constants;

public interface IKafkaConstants {
    public static String KAFKA_BROKERS = "localhost:9092";

    public static String GROUP_ID_CONFIG = "consumerGroup10";

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 5;

    public static String OFFSET_RESET_EARLIER = "earliest";

    public static Integer MAX_POLL_RECORDS = 1;
}
