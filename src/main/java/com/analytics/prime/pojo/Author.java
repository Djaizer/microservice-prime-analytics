package com.analytics.prime.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="authors")
public class Author {

	@Id
	private final String id;
	
	private final String name;
}