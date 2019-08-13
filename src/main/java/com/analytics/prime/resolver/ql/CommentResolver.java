package com.analytics.prime.resolver.ql;

import com.analytics.prime.excpetion.NotFoundException;
import com.analytics.prime.pojo.Author;
import com.analytics.prime.pojo.Comment;
import com.analytics.prime.pojo.Post;
import com.analytics.prime.repository.AuthorRepository;
import com.analytics.prime.repository.PostRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentResolver implements GraphQLResolver<Comment> {
	
	private final AuthorRepository authRepository;
	private final PostRepository postRepository;
	
	public Author createdBy(Comment comment) {
		// not best way to deal with optional, but I can't be bothered to do more for demo :)
		return authRepository.findById(comment.getAuthorId()).orElseThrow(() -> new NotFoundException());
	}
	
	public Post belongsTo(Comment comment) {
		// not best way to deal with optional, but I can't be bothered to do more for demo :)
		return postRepository.findById(comment.getPostId()).orElseThrow(() -> new NotFoundException());
	}

}
