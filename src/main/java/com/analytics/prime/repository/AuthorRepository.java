package com.analytics.prime.repository;

import com.analytics.prime.pojo.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {

}