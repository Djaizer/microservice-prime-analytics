package com.analytics.prime.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection="comments")
public class Comment {

	@Id
	private final String id;
	
	@NotNull
	private final String authorId;
	
	@NotNull	
	private final String postId;
	
	private final String text;	
}
