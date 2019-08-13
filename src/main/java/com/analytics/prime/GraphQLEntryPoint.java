package com.analytics.prime;

import com.analytics.prime.repository.AuthorRepository;
import com.analytics.prime.repository.CommentRepository;
import com.analytics.prime.repository.PostRepository;
import com.analytics.prime.resolver.ql.AuthorResolver;
import com.analytics.prime.resolver.ql.CommentResolver;
import com.analytics.prime.resolver.ql.PostResolver;
import com.analytics.prime.resolver.root.Mutation;
import com.analytics.prime.resolver.root.Query;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

public class GraphQLEntryPoint extends SimpleGraphQLServlet {
	
	public GraphQLEntryPoint(PostRepository postRepository, AuthorRepository authRepository, CommentRepository commentRepository) {
		super(buildSchema(postRepository,authRepository, commentRepository));
	}

	private static GraphQLSchema buildSchema(PostRepository postRepository, AuthorRepository authRepository, CommentRepository commentRepository) {
		return SchemaParser
				.newParser()
				.file("schema.graphqls")
				.resolvers(
						new Query(postRepository, authRepository),
						new Mutation(authRepository),						
						new PostResolver(authRepository,commentRepository),
						new AuthorResolver(postRepository),
						new CommentResolver(authRepository,postRepository))
				.build()
				.makeExecutableSchema();
	}	

}
