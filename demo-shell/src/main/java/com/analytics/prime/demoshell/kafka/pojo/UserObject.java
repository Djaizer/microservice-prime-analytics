package com.analytics.prime.demoshell.kafka.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private MessageType type;
    private Integer number;

}
