package com.analytics.prime.resolver.root;

import com.analytics.prime.excpetion.NotFoundException;
import com.analytics.prime.pojo.Author;
import com.analytics.prime.repository.AuthorRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
	
	private final AuthorRepository authRepo;

	public Author addAuthor(String name) {
		return authRepo.save(new Author(null, name));
	}
	
	public Author removeAuthour(String id) {
		// not best way to deal with optional, but I can't be bothered to do more for demo :)
		Author auth = authRepo.findById(id).orElseThrow(() -> new NotFoundException());
		authRepo.delete(auth);
		return auth;
	}
}
