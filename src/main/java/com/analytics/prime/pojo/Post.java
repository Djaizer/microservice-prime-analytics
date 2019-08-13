package com.analytics.prime.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection="posts")
public class Post {

	@Id
	private final String id;
		
	@NotNull
	private final String authorId;
	
	private final String title;
	private final String body;
}
