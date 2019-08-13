package com.analytics.prime;

import com.analytics.prime.excpetion.NotFoundException;
import com.analytics.prime.pojo.Author;
import com.analytics.prime.pojo.Comment;
import com.analytics.prime.pojo.Post;
import com.analytics.prime.repository.AuthorRepository;
import com.analytics.prime.repository.CommentRepository;
import com.analytics.prime.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestControllerV1 {

	
	@Autowired
	AuthorRepository authR;
	
	@Autowired
	PostRepository postR;
	
	@Autowired
	CommentRepository commentR;
	
	
	@RequestMapping(path="/authors")
	public List<Author> getAllAuthors() {
		return authR.findAll();
	}

	@RequestMapping(path="/authors/{id}")
	public Author getAuthorById(@PathVariable String id) {
		return authR.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	@RequestMapping(path="/posts")
	public List<Post> getAllPosts(@RequestParam(value="author_id",required = false) String authId) {
		if(authId == null) 
			return postR.findAll();
		else 
			return postR.findByAuthorId(authId);
	}

	@RequestMapping(path="/comments")
	public List<Comment> getAllComments(@RequestParam(value="post_id",required = false) String postId) {
		if(postId == null)
			return commentR.findAll();
		else
			return commentR.findByPostId(postId);
	}

}
