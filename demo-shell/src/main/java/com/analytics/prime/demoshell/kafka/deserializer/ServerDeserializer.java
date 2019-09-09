package com.analytics.prime.demoshell.kafka.deserializer;


import com.analytics.prime.demoshell.kafka.pojo.ServerObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ServerDeserializer implements Deserializer<ServerObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public ServerObject deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        ServerObject object = null;
        try {
            object = mapper.readValue(data, ServerObject.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes " + exception);
        }
        return object;
    }

    @Override
    public void close() {
    }
}

//java -jar target\demo-shell-0.0.1-SNAPSHOT.jar