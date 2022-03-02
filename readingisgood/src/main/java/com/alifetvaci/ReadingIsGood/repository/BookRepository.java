package com.alifetvaci.ReadingIsGood.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.alifetvaci.ReadingIsGood.models.Book;

public interface BookRepository extends MongoRepository<Book, String>{

}
