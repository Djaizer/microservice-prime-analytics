package com.analytics.prime.resolver.ql;

import com.analytics.prime.pojo.Author;
import com.analytics.prime.pojo.Post;
import com.analytics.prime.repository.PostRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuthorResolver implements GraphQLResolver<Author> {
	
	private final PostRepository postRepository;
	
	public List<Post> posts(Author auth) {
		return postRepository.findByAuthorId(auth.getId());
	}
}
