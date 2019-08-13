package com.analytics.prime.resolver.ql;

import com.analytics.prime.excpetion.NotFoundException;
import com.analytics.prime.pojo.Author;
import com.analytics.prime.pojo.Comment;
import com.analytics.prime.pojo.Post;
import com.analytics.prime.repository.AuthorRepository;
import com.analytics.prime.repository.CommentRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostResolver implements GraphQLResolver<Post> {
	
	private final AuthorRepository authRepository;	
	
	private final CommentRepository commentRepository;
	
	public Author createdBy(Post post) {
		// not best way to deal with optional, but I can't be bothered to do more for demo :)
		return authRepository.findById(post.getAuthorId()).orElseThrow(() -> new NotFoundException());
	}
	
	public List<Comment> comments(Post post) {
		return commentRepository.findByPostId(post.getId());
	}	
	
}
