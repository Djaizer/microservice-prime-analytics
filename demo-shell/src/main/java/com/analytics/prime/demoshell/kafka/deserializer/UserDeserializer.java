package com.analytics.prime.demoshell.kafka.deserializer;


import com.analytics.prime.demoshell.kafka.pojo.UserObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UserDeserializer implements Deserializer<UserObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public UserObject deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        UserObject object = null;
        try {
            object = mapper.readValue(data, UserObject.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes " + exception);
        }
        return object;
    }

    @Override
    public void close() {
    }
}