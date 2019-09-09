package com.analytics.prime.demoshell.kafka.producer;


import com.analytics.prime.demoshell.kafka.constants.IKafkaConstants;
import com.analytics.prime.demoshell.kafka.pojo.ServerObject;
import com.analytics.prime.demoshell.kafka.pojo.UserObject;
import com.analytics.prime.demoshell.kafka.serializer.ServerSerializer;
import com.analytics.prime.demoshell.kafka.serializer.UserSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

public class ProducerCreator {

	public static Producer<Long, UserObject> createUserProducer(String clientId) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

	public static Producer<Long, ServerObject> createServerProducer(String clientId) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ServerSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

}