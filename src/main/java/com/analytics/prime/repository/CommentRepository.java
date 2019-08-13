package com.analytics.prime.repository;

import com.analytics.prime.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
	List<Comment> findByPostId(String postId);
}