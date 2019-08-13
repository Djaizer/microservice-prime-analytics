package com.analytics.prime.resolver.root;

import com.analytics.prime.pojo.Author;
import com.analytics.prime.pojo.Post;
import com.analytics.prime.repository.AuthorRepository;
import com.analytics.prime.repository.PostRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

	private final PostRepository postRepository;
	private final AuthorRepository authRepo;

	public List<Post> allPosts() {
		return postRepository.findAll();
	}

	public List<Author> allAuthors() {
		return authRepo.findAll();
	}

}
